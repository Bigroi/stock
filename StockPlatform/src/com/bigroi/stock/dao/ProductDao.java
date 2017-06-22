package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.Product;

public interface ProductDao {
	
	List<Product> getAllProduct() throws DaoException;
	
	void add(Product product) throws DaoException;
	
	void delete(long id) throws DaoException;
	
	void update(long id, Product product) throws DaoException;

}
