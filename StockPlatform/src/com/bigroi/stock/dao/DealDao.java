package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Deal;

public interface DealDao {
	
    void add(Deal deal) throws DaoException;

}