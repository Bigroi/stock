package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.Bid;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.jobs.trade.TradeBid;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;
import com.bigroi.stock.messager.message.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TradeService;

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
			List<TradeLot> tradeLots = new ArrayList<>();
			List<TradeTender> tradeTenders = new ArrayList<>();
			dealDao.getPosibleDeals(tradeLots, tradeTenders, product.getId());
			
			tradeLots.forEach(l -> l.setProduct(product));
			tradeTenders.forEach(t -> t.setProduct(product));
			
			while(tradeTenders.size() > 0 && tradeLots.size() > 0){
				
				List<? extends TradeBid> majorBids = getMinVolumeBids(tradeLots, tradeTenders);
				
				TradeBid majorBid = getBestBid(majorBids);
				
				createDealsForBid(majorBid, product);
				
				removeAllZeroBids(tradeTenders, tradeLots);
			}
			
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	private void createDealsForBid(TradeBid bid, Product product) throws ServiceException{
		while(bid.getMaxVolume() > 0 && bid.getPosiblePartners().size() > 0){
			TradeBid partner = bid.getBestPartner();
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

	private Deal createDeal(TradeBid bid, TradeBid partner) {
		int volume = Math.min(bid.getMaxVolume(), partner.getMaxVolume());
		bid.setMaxVolume(bid.getMaxVolume() - volume);
		partner.setMaxVolume(partner.getMaxVolume() - volume);
		
		double maxTransportPrice = TradeBid.getDistancePrice(bid, partner);
		
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
	
	private int getTotalVolume(List<? extends Bid> tradeBids) {
		int result = 0;
		for (Bid bid : tradeBids){
			result += bid.getMaxVolume();
		}
		return result;
	}
	
	private void removeAllZeroBids(List<TradeTender> tradeTenders, List<TradeLot> tradeLots) {
		for (TradeTender tender : new ArrayList<>(tradeTenders)){
			if (tender.getPosiblePartners().size() == 0){
				tradeTenders.remove(tender);
			}
		}
		for (TradeLot lot : new ArrayList<>(tradeLots)){
			if (lot.getPosiblePartners().size() == 0){
				tradeLots.remove(lot);
			}
		}
	}
}
