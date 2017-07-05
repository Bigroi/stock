package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.PreDeal;

public interface PreDealDao {
	
    void add(PreDeal preDeal) throws DaoException;
	
	void delete(long id) throws DaoException;
	
	void deleteAll() throws DaoException;
	
	void update(long id, PreDeal preDeal) throws DaoException;
	
	List<PreDeal> getAllPreDeal() throws DaoException;
	
	

}
