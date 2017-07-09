package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.Product;

public interface ProductDao {
	
	List<Product> getAllProduct() throws DaoException;
	
	void add(Product product) throws DaoException;
	
	boolean deletedById(long id) throws DaoException;
	
	boolean updateById(Product product) throws DaoException;
	
	Product getById(long id) throws DaoException;
}
