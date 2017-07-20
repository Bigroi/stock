package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.PreDeal;

public interface PreDealDao {
	
    void add(PreDeal preDeal) throws DaoException;
	
	boolean deletedById(long id) throws DaoException;
	
	void deleteAll() throws DaoException;
	
	boolean updateById(PreDeal preDeal) throws DaoException;
	
	List<PreDeal> getAllPreDeal() throws DaoException;
	
	 PreDeal getById(long id) throws DaoException ;
	

}
