package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.Tender;

public interface TenderDao {
	
    void add(Tender tender) throws DaoException;
	
	boolean deletedById(long id) throws DaoException;
	
	boolean updateById (Tender tender) throws DaoException;
	
	Tender getById(long id) throws DaoException;
	
	List<Tender> getByCustomerId(long customerId) throws DaoException;
	
	List<Tender> getByProductId(long productId) throws DaoException;
	
	List<Tender> getByProductIdInGameOrderMaxPrice(long productId) throws DaoException;
	
	List<Tender> getAllInGame() throws DaoException;

}
