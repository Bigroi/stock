package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

public interface DealDao {
	
	void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) throws DaoException;

	Deal getById(long id, long companyId) throws DaoException;

	List<Deal> getOnApprove() throws DaoException;

	void deleteOnApprove() throws DaoException;

	void add(List<Deal> deals) throws DaoException;

	List<Deal> getByCompanyId(long companyId) throws DaoException;

	public void setBuyerStatus(Deal deal) throws DaoException;

	public void setSellerStatus(Deal deal) throws DaoException;
	
	public List<Deal> getListBySellerAndBuyerApproved() throws DaoException;

}