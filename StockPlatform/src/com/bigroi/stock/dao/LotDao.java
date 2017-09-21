package com.bigroi.stock.dao;

import java.util.List;


import com.bigroi.stock.bean.Lot;

public interface LotDao {
	
	void add(Lot lot) throws DaoException;
	
	boolean deletedById(long id) throws DaoException;
	
	boolean updateById( Lot lot) throws DaoException;
	
	Lot getById(long id) throws DaoException;
	
	List<Lot> getBySellerId(long salerId) throws DaoException;
	
	List<Lot> getByProductId(long productId) throws DaoException;
	
	List<Lot> getByProductIdInGameOrderMinPrice(long productId) throws DaoException;
	
	List<Lot> getAllInGame() throws DaoException;

	 boolean setStatusCancel( long sellerId) throws DaoException;

	
 
}
