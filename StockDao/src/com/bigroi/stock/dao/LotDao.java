package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;

public interface LotDao {
	
	void add(Lot lot) throws DaoException;
	
	boolean update(Lot lot, long companyId) throws DaoException;
	
	Lot getById(long id, long companyId) throws DaoException;
	
	List<Lot> getByCompanyId(long companyId) throws DaoException;
	
	void update(Collection<Lot> lotsToUpdate) throws DaoException;

	void delete(long id, long companyId) throws DaoException;

	boolean setStatusById(long id, long companyId, BidStatus active) throws DaoException;

	boolean setStatusByCompanyId(long companyId, BidStatus inactive) throws DaoException;

	boolean setStatusByProductId(long id, BidStatus inactive) throws DaoException;

	List<Lot> getActive() throws DaoException;

	List<Lot> getActiveByProductId(long productId) throws DaoException;
	
	void closeLots() throws DaoException;

	List<Lot> getByDescription(String description) throws DaoException;

	void deleteByDescription(String description) throws DaoException;
}
