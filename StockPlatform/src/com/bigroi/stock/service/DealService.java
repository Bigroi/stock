package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

public interface DealService {

	PreDeal getById(long id) throws ServiceException;
	
	void setApprovedByCustomer(long id) throws ServiceException;
	
	void setApprovedBySeller(long preDealId) throws ServiceException;

	void cancel(long preDealId, boolean seller) throws ServiceException;

	void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) throws ServiceException;

	void add(PreDeal preDeal) throws ServiceException;
}
