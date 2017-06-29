package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Deals;

public interface DealsDao {
	
    void add(Deals deals) throws DaoException;
	
	void delete(long id) throws DaoException;
	
	void update(long id, Deals deals) throws DaoException;
}
