package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.util.exception.StockRuntimeException;

@Repository
public class TradeServiceImpl implements TradeService{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private DealDao dealDao;
	@Autowired
	private LotDao lotDao;
	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private AddressService addressService;
	
	
	@Autowired
	private DealConfirmationMessageForCustomer dealConfirmationMessageForCustomer;
	@Autowired
	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
	
	private Set<Tender> tendersToUpdate = new HashSet<>();
	private Set<Lot> lotsToUpdate = new HashSet<>();
	private List<Deal> deals = new ArrayList<>();
	
	@Override
	@Transactional
	public void trade() throws ServiceException{
		try{
			List<Product> list = productDao.getAllActiveProducts();
			for (Product product : list){
				productTrade(product);
			}
			dealDao.add(deals);
			lotDao.update(lotsToUpdate);
			tenderDao.update(tendersToUpdate);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	private void productTrade(Product product) throws ServiceException{
		try{
			List<Lot> tradeLots = new ArrayList<>();
			List<Tender> tradeTenders = new ArrayList<>();
			dealDao.getPosibleDeals(tradeLots, tradeTenders, product.getId());
			
			tradeLots.forEach(l -> l.setProduct(product));
			tradeTenders.forEach(t -> t.setProduct(product));
			
			while(!tradeTenders.isEmpty() && !tradeLots.isEmpty()){
				
				List<? extends Bid> majorBids = getMinVolumeBids(tradeLots, tradeTenders);
				
				Bid majorBid = getBestBid(majorBids);
				
				createDealsForBid(majorBid, product);
				
				removeAllZeroBids(tradeTenders, tradeLots);
			}
			
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	private void createDealsForBid(Bid bid, Product product) throws ServiceException{
		while(bid.getMaxVolume() > 0 && !bid.getPosiblePartners().isEmpty()){
			Bid partner = bid.getBestPartner();
			Deal deal = createDeal(bid, partner);
			deal.setProduct(product);
			sendConfimationMails(deal);
			deals.add(deal);
			
			if ( partner.getMaxVolume() < partner.getMinVolume()){
				partner.removeFromPosiblePartners();
			}
			
			if ( bid.getMaxVolume() < bid.getMinVolume()){
				bid.removeFromPosiblePartners();
			}
		}
	}
	
	private void sendConfimationMails(Deal deal) throws ServiceException {
		try{
			dealConfirmationMessageForCustomer.send(deal);
			dealConfirmationMessageForSeller.send(deal);
		} catch (MessageException e) {
			throw new ServiceException(e);
		}
	}

	private Deal createDeal(Bid bid, Bid partner) {
		int volume = Math.min(bid.getMaxVolume(), partner.getMaxVolume());
		bid.setMaxVolume(bid.getMaxVolume() - volume);
		partner.setMaxVolume(partner.getMaxVolume() - volume);
		
		double maxTransportPrice = Bid.getDistancePrice(bid, partner);
		
		Lot lot;
		Tender tender;
		if (bid instanceof Lot){
			lot = (Lot) bid;
			tender = (Tender) partner;
		} else {
			lot = (Lot) partner;
			tender = (Tender) bid;
		}
		Deal deal = new Deal(lot, tender, volume, maxTransportPrice);
		
		lotsToUpdate.add(lot);
		tendersToUpdate.add(tender);
		return deal;
	}
	
	private Bid getBestBid(List<? extends Bid> bids) {
		Bid bid = Collections
				.min(bids, 
					(o1, o2) -> o1.getPosiblePartners().size() - o2.getPosiblePartners().size()
				);
		bids = bids.stream()
				.filter(b -> b.getPosiblePartners().size() == bid.getPosiblePartners().size())
				.collect(Collectors.toList());
		
		return Collections
				.min(bids, 
						(o1, o2) -> o1.getMaxVolume() - o2.getMaxVolume()
					);
	}

	private List<? extends Bid> getMinVolumeBids(List<Lot> tradeLots, List<Tender> tradeTenders) {
		if (getTotalVolume(tradeLots) < getTotalVolume(tradeTenders)){
			return tradeLots;
		} else {
			return tradeTenders;
		}
	}
	
	private int getTotalVolume(List<? extends Bid> tradeBids) {
		int result = 0;
		for (Bid bid : tradeBids){
			result += bid.getMaxVolume();
		}
		return result;
	}
	
	private void removeAllZeroBids(List<Tender> tradeTenders, List<Lot> tradeLots) {
		for (Tender tender : new ArrayList<>(tradeTenders)){
			if (tender.getPosiblePartners().isEmpty()){
				tradeTenders.remove(tender);
			}
		}
		for (Lot lot : new ArrayList<>(tradeLots)){
			if (lot.getPosiblePartners().isEmpty()){
				tradeLots.remove(lot);
			}
		}
	}

	@Override
	public List<Deal> testTrade(String sessionId) throws ServiceException {
		try{
			lotDao = Mockito.mock(LotDao.class);
			tenderDao = Mockito.mock(TenderDao.class);
			dealConfirmationMessageForCustomer = Mockito.mock(DealConfirmationMessageForCustomer.class);
			dealConfirmationMessageForSeller = Mockito.mock(DealConfirmationMessageForSeller.class);
			
			DealDao realDealDao = dealDao;
			dealDao = Mockito.mock(DealDao.class);
			Mockito.doAnswer(x -> testDaalDaoAnser(x, realDealDao, sessionId))
			.when(dealDao).getPosibleDeals(Mockito.any(), Mockito.any(), Mockito.anyLong());
			
			trade();
			
			deals.stream().forEach(this::enrichAddress);
			
			return deals;
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	private void enrichAddress(Deal deal){
		try{
			deal.setBuyerAddress(addressService.getAddressById(deal.getBuyerAddressId(), 0));
			deal.setSellerAddress(addressService.getAddressById(deal.getSellerAddressId(), 0));
		} catch (ServiceException e) {
			throw new StockRuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Void testDaalDaoAnser(InvocationOnMock invocation, DealDao realDealDao, String sessionId){
		try{
			List<Lot> lots = (List<Lot>) invocation.getArguments()[0];
			List<Tender> tenders = (List<Tender>) invocation.getArguments()[1];
			long productId = (long) invocation.getArguments()[2];
			realDealDao.getTestPossibleDeals(lots, tenders, productId, sessionId);
			return null;
		}catch (DaoException e) {
			throw new StockRuntimeException(e);
		}
	}
}
