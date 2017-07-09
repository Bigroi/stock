package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Blacklist;

public interface BlacklistDao {
	
	    void add(Blacklist blacklist) throws DaoException;
		
		boolean deletedById(long id) throws DaoException;
	
		boolean updateById(Blacklist blacklist) throws DaoException;
}
