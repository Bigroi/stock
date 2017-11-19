package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;

public interface TenderDao {
	
    void add(Tender tender) throws DaoException;
	
	boolean update(Tender tender) throws DaoException;
	
	Tender getById(long id) throws DaoException;
	
	List<Tender> getByCustomerId(long customerId) throws DaoException;
	
	List<Tender> getActiveByProductId(long productId) throws DaoException;
	
	List<Tender> getAllActive() throws DaoException;

	boolean setStatusByCustomerId(long customerId, Status status) throws DaoException;

	boolean  setStatusByProductId(long productId, Status status) throws DaoException;
	
	boolean  setStatusById(long id, Status status) throws DaoException;

	void update(Collection<Tender> tendersToUpdate) throws DaoException;

}
