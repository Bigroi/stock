package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;

public interface DealDao {
	
	void getPosibleDeals(List<Lot> tradeLots, List<Tender> tradeTenders, long productId) throws DaoException;

	Deal getById(long id) throws DaoException;

	List<Deal> getOnApprove() throws DaoException;

	void deleteOnApprove() throws DaoException;

	void add(List<Deal> deals) throws DaoException;

	List<Deal> getByCompanyId(long companyId) throws DaoException;

	public void setBuyerStatus(Deal deal) throws DaoException;

	public void setSellerStatus(Deal deal) throws DaoException;
	
	public List<Deal> getListBySellerAndBuyerApproved() throws DaoException;

}