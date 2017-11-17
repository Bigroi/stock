package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.jobs.trade.TradeBid;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.util.Generator;

public class TradeServiceImpl implements TradeService{

	private ProductDao productDao;
	private PreDealDao preDealDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
	private Set<Tender> tendersToUpdate = new HashSet<>();
	private Set<Lot> lotsToUpdate = new HashSet<>();
	private List<PreDeal> deals = new ArrayList<>();
	
	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}
	
	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public void setPreDealDao(PreDealDao preDealDao) {
		this.preDealDao = preDealDao;
	}
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@Transactional
	public void trade() throws ServiceException{
		try{
			List<Product> list = productDao.getAllActiveProducts();
			for (Product product : list){
				productTrade(product.getId());
			}
			lotDao.update(lotsToUpdate);
			tenderDao.update(tendersToUpdate);
			preDealDao.add(deals);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	private void productTrade(long productId) throws ServiceException{
		try{
			List<TradeLot> tradeLots = new ArrayList<>();
			List<TradeTender> tradeTenders = new ArrayList<>();
			preDealDao.getPosibleDeals(tradeLots, tradeTenders, productId);
			removeAllZeroBids(tradeTenders, tradeLots);
			
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

	//Circle 3
	private void createDealsForBid(TradeBid bid){
		while(bid.getVolume() > 0 && bid.getPosiblePartners().size() > 0){
			TradeBid partner = bid.getBestPartner();
			
			deals.add(createPreDeal(bid, partner));
			
			if (partner.getVolume() == 0){
				partner.removeFromPosiblePartners();
			}
			
			if (bid.getVolume() == 0){
				bid.removeFromPosiblePartners();
			}
		}
	}
	
	//step 5
	private PreDeal createPreDeal(TradeBid bid, TradeBid partner) {
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
		PreDeal preDeal = new PreDeal();
		preDeal.setCustApprovBool(false);
		preDeal.setCustomerHashCode(Generator.generateLinkKey(15));
		preDeal.setDealDate(new Date());
		preDeal.setLotId(lot.getId());
		preDeal.setSellerApprovBool(false);
		preDeal.setSellerHashCode(Generator.generateLinkKey(15));
		preDeal.setTenderId(tender.getId());
		preDeal.setVolume(volume);
		
		lotsToUpdate.add(lot);
		tendersToUpdate.add(tender);
		return preDeal;
	}
	
	private int getVolume(TradeBid bid, TradeBid partner) {
		int volume = Math.min(bid.getVolume(), partner.getVolume());
		bid.setVolume(bid.getVolume() - volume);
		partner.setVolume(partner.getVolume() - volume);
		return volume;
	}

	//step 3
	private TradeBid getMinVolumeBid(List<? extends TradeBid> majorBids) {
		return Collections.min(majorBids, new Comparator<TradeBid>() {

			@Override
			public int compare(TradeBid o1, TradeBid o2) {
				return o1.getTotalPosibleVolume() - o2.getTotalPosibleVolume();
			}
		});
	}

	//step 2
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
			result += bid.getVolume();
		}
		return result;
	}
	
	//step 1
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
