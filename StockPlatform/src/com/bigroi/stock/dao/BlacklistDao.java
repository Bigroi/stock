package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Blacklist;

public interface BlacklistDao {
	
    void add(Blacklist blacklist) throws DaoException;
	
}