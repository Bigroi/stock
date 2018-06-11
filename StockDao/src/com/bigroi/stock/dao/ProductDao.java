package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ProductForUI;

public interface ProductDao {
	
	List<Product> getAllProducts() throws DaoException;
	
	List<Product> getAllActiveProducts() throws DaoException;
	
	List<ProductForUI> getAllActiveProductsForUI() throws DaoException;
	
	void add(Product product) throws DaoException;
	
	boolean update(Product product) throws DaoException;
	
	Product getById(long id) throws DaoException;
	
}
