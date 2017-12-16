package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.BidStatus;

public interface LotDao {
	
	void add(Lot lot) throws DaoException;
	
	boolean update(Lot lot) throws DaoException;
	
	Lot getById(long id) throws DaoException;
	
	List<Lot> getBySellerId(long salerId) throws DaoException;
	
	List<Lot> getActiveByProductId(long productId) throws DaoException;
	
	List<Lot> getActive() throws DaoException;

	void update(Collection<Lot> lotsToUpdate) throws DaoException;

	void deleteById(long id) throws DaoException;

	boolean setStatusById(long id, BidStatus active) throws DaoException;

	boolean setStatusBySellerId(long companyId, BidStatus inactive) throws DaoException;

	boolean setStatusByProductId(long id, BidStatus inactive) throws DaoException;
}
