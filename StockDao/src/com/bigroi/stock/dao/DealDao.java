package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;

public interface DealDao {
	
	void getPosibleDeals(List<Lot> tradeLots, List<Tender> tradeTenders, long productId);

	Deal getById(long id);

	List<Deal> getOnApprove();

	void deleteOnApprove();

	void add(List<Deal> deals);

	List<Deal> getByCompanyId(long companyId);

	public void setBuyerStatus(Deal deal);

	public void setSellerStatus(Deal deal);
	
	public List<Deal> getListBySellerAndBuyerApproved();

	void getTestPossibleDeals(List<Lot> lots, List<Tender> tenders, long productId, String sessionId);

}