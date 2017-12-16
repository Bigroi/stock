package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;

public interface TenderDao {
	
    void add(Tender tender) throws DaoException;
	
	boolean update(Tender tender) throws DaoException;
	
	Tender getById(long id) throws DaoException;
	
	List<Tender> getByCustomerId(long customerId) throws DaoException;
	
	List<Tender> getActiveByProductId(long productId) throws DaoException;
	
	List<Tender> getAllActive() throws DaoException;

	boolean setStatusByCustomerId(long customerId, BidStatus status) throws DaoException;

	boolean  setStatusByProductId(long productId, BidStatus status) throws DaoException;
	
	boolean  setStatusById(long id, BidStatus status) throws DaoException;

	void update(Collection<Tender> tendersToUpdate) throws DaoException;

	void deleteById(long id) throws DaoException;

}
