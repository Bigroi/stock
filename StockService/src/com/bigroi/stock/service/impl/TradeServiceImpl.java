package com.bigroi.stock.service.impl;

import java.lang.reflect.Field;
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

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.TradeBid;
import com.bigroi.stock.bean.db.TradeLot;
import com.bigroi.stock.bean.db.TradeTender;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForBuyer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.util.LabelUtil;
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
	private DealConfirmationMessageForBuyer dealConfirmationMessageForBuyer;
	@Autowired
	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
	
	private Set<TradeTender> tendersToUpdate = new HashSet<>();
	private Set<TradeLot> lotsToUpdate = new HashSet<>();
	private List<Deal> deals = new ArrayList<>();
	
	private boolean canUse = false;
	
	@Override
	@Transactional
	public void trade(){
		if (!canUse){
			throw new StockRuntimeException("use newInstance() befor use");
		}
		List<Product> list = productDao.getAllActiveProducts();
		for (Product product : list){
			productTrade(product);
		}
		dealDao.add(deals);
		lotDao.update(lotsToUpdate);
		tenderDao.update(tendersToUpdate);
	}
	
	private void productTrade(Product product){
		List<TradeLot> tradeLots = new ArrayList<>();
		List<TradeTender> tradeTenders = new ArrayList<>();
		dealDao.getPosibleDeals(tradeLots, tradeTenders, product.getId());
		removeByDistance(tradeLots);
		
		while(!tradeTenders.isEmpty() && !tradeLots.isEmpty()){
			
			List<? extends TradeBid> majorBids = getMinVolumeBids(tradeLots, tradeTenders);
			
			TradeBid majorBid = getBestBid(majorBids);
			
			createDealsForBid(majorBid, product);
			
			removeAllZeroBids(tradeTenders, tradeLots);
		}
	}

	private void removeByDistance(List<? extends TradeBid> tradeBids) {
		for (TradeBid bid : tradeBids){
			List<TradeBid> partners = bid.getPosiblePartners();
			for (TradeBid partner : new ArrayList<>(partners)){
				double distance = TradeBid.getDistancePrice(bid, partner);
				if (partner.getDistance() < distance &&
						bid.getDistance() < distance){
					partner.getPosiblePartners().remove(bid);
					bid.getPosiblePartners().remove(partner);
				}
			}
		}
	}

	private void createDealsForBid(TradeBid bid, Product product){
		while(bid.getMaxVolume() > 0 && !bid.getPosiblePartners().isEmpty()){
			TradeBid partner = bid.getBestPartner();
			Deal deal = createDeal(bid, partner);
			deal.setProductId(product.getId());
			deal.setProductName(product.getName());
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
	
	private void sendConfimationMails(Deal deal) {
		dealConfirmationMessageForBuyer.send(deal, LabelUtil.parseString(deal.getBuyerLanguage()));
		dealConfirmationMessageForSeller.send(deal, LabelUtil.parseString(deal.getSellerLanguage()));
	}

	private Deal createDeal(TradeBid bid, TradeBid partner) {
		int volume = Math.min(bid.getMaxVolume(), partner.getMaxVolume());
		bid.setMaxVolume(bid.getMaxVolume() - volume);
		partner.setMaxVolume(partner.getMaxVolume() - volume);
		
		double maxTransportPrice = TradeBid.getDistancePrice(bid, partner);
		
		TradeLot lot;
		TradeTender tender;
		if (bid instanceof TradeLot){
			lot = (TradeLot) bid;
			tender = (TradeTender) partner;
		} else {
			lot = (TradeLot) partner;
			tender = (TradeTender) bid;
		}
		Deal deal = new Deal(lot, tender, volume, maxTransportPrice);
		
		lotsToUpdate.add(lot);
		tendersToUpdate.add(tender);
		return deal;
	}
	
	private TradeBid getBestBid(List<? extends TradeBid> bids) {
		TradeBid bid = Collections
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

	private List<? extends TradeBid> getMinVolumeBids(List<TradeLot> tradeLots, List<TradeTender> tradeTenders) {
		if (getTotalVolume(tradeLots) < getTotalVolume(tradeTenders)){
			return tradeLots;
		} else {
			return tradeTenders;
		}
	}
	
	private int getTotalVolume(List<? extends TradeBid> tradeBids) {
		int result = 0;
		for (TradeBid bid : tradeBids){
			result += bid.getMaxVolume();
		}
		return result;
	}
	
	private void removeAllZeroBids(List<TradeTender> tradeTenders, List<TradeLot> tradeLots) {
		for (TradeTender tender : new ArrayList<>(tradeTenders)){
			if (tender.getPosiblePartners().isEmpty()){
				tradeTenders.remove(tender);
			}
		}
		for (TradeLot lot : new ArrayList<>(tradeLots)){
			if (lot.getPosiblePartners().isEmpty()){
				tradeLots.remove(lot);
			}
		}
	}

	@Override
	public List<Deal> testTrade(String sessionId) {
		if (!canUse){
			throw new StockRuntimeException("use newInstance() befor use");
		}
		lotDao = Mockito.mock(LotDao.class);
		tenderDao = Mockito.mock(TenderDao.class);
		dealConfirmationMessageForBuyer = Mockito.mock(DealConfirmationMessageForBuyer.class);
		dealConfirmationMessageForSeller = Mockito.mock(DealConfirmationMessageForSeller.class);
		
		DealDao realDealDao = dealDao;
		dealDao = Mockito.mock(DealDao.class);
		Mockito.doAnswer(x -> testDaalDaoAnser(x, realDealDao, sessionId))
		.when(dealDao).getPosibleDeals(Mockito.any(), Mockito.any(), Mockito.anyLong());
		
		trade();
		
		deals.stream().forEach(this::enrichAddress);
		
		return deals;
	}
	
	private void enrichAddress(Deal deal){
		CompanyAddress buyerAddress = addressService.getAddressById(deal.getBuyerAddressId(), 0);
		deal.setBuyerAddress(buyerAddress.getAddress());
		deal.setBuyerCity(buyerAddress.getCity());
		deal.setBuyerCountry(buyerAddress.getCity());
		
		CompanyAddress sellerAddress = addressService.getAddressById(deal.getSellerAddressId(), 0);
		deal.setSellerAddress(sellerAddress.getAddress());
		deal.setSellerCity(sellerAddress.getCity());
		deal.setSellerCountry(sellerAddress.getCity());
	}
	
	@SuppressWarnings("unchecked")
	private Void testDaalDaoAnser(InvocationOnMock invocation, DealDao realDealDao, String sessionId){
		List<TradeLot> lots = (List<TradeLot>) invocation.getArguments()[0];
		List<TradeTender> tenders = (List<TradeTender>) invocation.getArguments()[1];
		long productId = (long) invocation.getArguments()[2];
		realDealDao.getTestPossibleDeals(lots, tenders, productId, sessionId);
		return null;
	}

	@Override
	public TradeService newInstance() {
		try {
			TradeServiceImpl instance = new TradeServiceImpl();
			for (Field field : getClass().getDeclaredFields()){
				if (field.getAnnotation(Autowired.class) != null){
					field.set(instance, field.get(this));
				}
			}
			instance.canUse = true;
			return instance;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new StockRuntimeException(e);
		}
	}
}
