package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;

public interface LotDao {
	
	void add(Lot lot) throws DaoException;
	
	boolean update(Lot lot) throws DaoException;
	
	Lot getById(long id) throws DaoException;
	
	List<Lot> getBySellerId(long salerId) throws DaoException;
	
	List<Lot> getActiveByProductId(long productId) throws DaoException;
	
	List<Lot> getActive() throws DaoException;

	boolean setStatusBySellerId(long sellerId, Status status) throws DaoException;
	 
	boolean setStatusByProductId(long productId, Status status) throws DaoException;
	 
	boolean setStatusById(long id, Status status) throws DaoException;

	void update(Collection<Lot> lotsToUpdate) throws DaoException;
}
