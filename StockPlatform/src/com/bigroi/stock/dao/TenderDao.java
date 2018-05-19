package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Tender;

public interface TenderDao {
	
    void add(Tender tender) throws DaoException;
	
	boolean update(Tender tender, long companyId) throws DaoException;
	
	Tender getById(long id, long companyId) throws DaoException;
	
	List<Tender> getByCompanyId(long companyId) throws DaoException;
	
	boolean setStatusByCompanyId(long companyId, BidStatus status) throws DaoException;

	boolean setStatusByProductId(long productId, BidStatus status) throws DaoException;
	
	void setStatusById(long id, long companyId, BidStatus status) throws DaoException;

	void update(Collection<Tender> tendersToUpdate) throws DaoException;

	void delete(long id, long companyId) throws DaoException;

	List<Tender> getActive() throws DaoException;

	List<Tender> getActiveByProductId(long productId) throws DaoException;

}
