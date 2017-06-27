package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Tender;

public interface TenderDao {
	
    void add(Tender tender) throws DaoException;
	
	void delete(long id) throws DaoException;
	
	void update(long id, Tender tender) throws DaoException;
	
	Tender getById(long id) throws DaoException;
	
	Tender getcustomerId(long customerId) throws DaoException;

}
