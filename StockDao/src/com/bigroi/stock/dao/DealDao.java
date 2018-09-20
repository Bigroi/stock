package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.TradeLot;
import com.bigroi.stock.bean.db.TradeTender;

public interface DealDao {
	
	void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId);

	Deal getById(long id);

	List<Deal> getOnApprove();

	void deleteOnApprove();

	void add(List<Deal> deals);

	List<Deal> getByCompanyId(long companyId);

	public void setBuyerStatus(Deal deal);

	public void setSellerStatus(Deal deal);
	
	public List<Deal> getListBySellerAndBuyerApproved();

	void getTestPossibleDeals(List<TradeLot> lots, List<TradeTender> tenders, long productId, String sessionId);

}