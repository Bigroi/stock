package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.Lot;

public interface LotDao {
	
	void add(Lot lot) throws DaoException;
	
	void delete(long id) throws DaoException;
	
	void update(long id, Lot lot) throws DaoException;
	
	Lot getById(long id) throws DaoException;
	
	List<Lot> getBySalerId(long salerId) throws DaoException;

}
