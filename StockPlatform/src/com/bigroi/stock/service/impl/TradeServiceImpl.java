package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.jobs.trade.TradeBid;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TradeService;

public class TradeServiceImpl implements TradeService{

	private ProductDao productDao;
	private DealDao dealDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
	private Set<Tender> tendersToUpdate = new HashSet<>();
	private Set<Lot> lotsToUpdate = new HashSet<>();
	private List<Deal> deals = new ArrayList<>();
	
	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}
	
	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public void setDealDao(DealDao dealDao) {
		this.dealDao = dealDao;
	}
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@Override
	@Transactional
	public void trade() throws ServiceException{
		try{
			List<Product> list = productDao.getAllActiveProducts();
			for (Product product : list){
				productTrade(product.getId());
			}
			lotDao.update(lotsToUpdate);
			tenderDao.update(tendersToUpdate);
			dealDao.add(deals);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	private void productTrade(long productId) throws ServiceException{
		try{
			List<TradeLot> tradeLots = new ArrayList<>();
			List<TradeTender> tradeTenders = new ArrayList<>();
			dealDao.getPosibleDeals(tradeLots, tradeTenders, productId);
			
			while(tradeTenders.size() > 0 && tradeLots.size() > 0){
				
				List<? extends TradeBid> majorBids = getMinVolumeBids(tradeLots, tradeTenders);
				
				TradeBid majorBid = getMinVolumeBid(majorBids);
				
				createDealsForBid(majorBid);
				
				removeAllZeroBids(tradeTenders, tradeLots);
			}
			
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	private void createDealsForBid(TradeBid bid) throws ServiceException{
		while(bid.getMaxVolume() > 0 && bid.getPosiblePartners().size() > 0){
			TradeBid partner = bid.getBestPartner();
			Deal deal = createDeal(bid, partner);
			sendConfimationMails(deal);
			deals.add(deal);
			
			if (partner.getMaxVolume() == 0 || partner.getMaxVolume() < partner.getMinVolume()){
				partner.removeFromPosiblePartners();
			}
			
			if (bid.getMaxVolume() == 0 || bid.getMaxVolume() < bid.getMinVolume()){
				bid.removeFromPosiblePartners();
			}
		}
	}
	
	private void sendConfimationMails(Deal deal) throws ServiceException {
		try{
			Message<Deal> message = MessagerFactory.getDealConfirmationMessageForCustomer();
			message.setDataObject(deal);
			message.send();
			
			message = MessagerFactory.getDealConfirmationMessageForSeller();
			message.setDataObject(deal);
			message.send();
		} catch (MessageException e) {
			throw new ServiceException(e);
		}
	}

	private Deal createDeal(TradeBid bid, TradeBid partner) {
		int volume = getVolume(bid, partner);
		
		Lot lot;
		Tender tender;
		if (bid instanceof Lot){
			lot = (Lot) bid;
			tender = (Tender) partner;
		} else {
			lot = (Lot) partner;
			tender = (Tender) bid;
		}
		Deal deal = new Deal(lot, tender, volume);
		
		lotsToUpdate.add(lot);
		tendersToUpdate.add(tender);
		return deal;
	}
	
	private int getVolume(TradeBid bid, TradeBid partner) {
		int volume = Math.min(bid.getMaxVolume(), partner.getMaxVolume());
		bid.setMaxVolume(bid.getMaxVolume() - volume);
		partner.setMaxVolume(partner.getMaxVolume() - volume);
		return volume;
	}

	private TradeBid getMinVolumeBid(List<? extends TradeBid> majorBids) {
		return Collections.min(majorBids, new Comparator<TradeBid>() {

			@Override
			public int compare(TradeBid o1, TradeBid o2) {
				return o1.getTotalPosibleVolume() - o2.getTotalPosibleVolume();
			}
		});
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
	
	public double distance(double lat1, double lat2, double lon1, double lon2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return distance;
	}
}
