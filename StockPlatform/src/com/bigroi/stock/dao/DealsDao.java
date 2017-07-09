package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Deals;

public interface DealsDao {
	
    void add(Deals deals) throws DaoException;
	
	boolean deletedById(long id) throws DaoException;
	
	boolean updateById(Deals deals) throws DaoException;
}
