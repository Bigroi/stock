package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;

public interface TenderDao {
	
    void add(Tender tender) throws DaoException;
	
	boolean update(Tender tender, long companyId) throws DaoException;
	
	Tender getById(long id, long companyId) throws DaoException;
	
	List<Tender> getByCustomerId(long customerId) throws DaoException;
	
	List<Tender> getActiveByProductId(long productId) throws DaoException;
	
	List<Tender> getAllActive() throws DaoException;

	boolean setStatusByCustomerId(long customerId, BidStatus status) throws DaoException;

	boolean  setStatusByProductId(long productId, BidStatus status) throws DaoException;
	
	void setStatusById(List<Long> ids, long companyId, BidStatus status) throws DaoException;

	void update(Collection<Tender> tendersToUpdate) throws DaoException;

	void delete(List<Long> ids, long companyId) throws DaoException;

}
