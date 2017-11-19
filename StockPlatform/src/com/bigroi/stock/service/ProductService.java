package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Product;

public interface ProductService {

	List<Product> getAllProducts() throws ServiceException;

	List<Product> getAllActiveProducts() throws ServiceException;
	
	void delete(long id) throws ServiceException;
	
	Product getProductById(long id) throws ServiceException;
	
	void merge(Product product) throws ServiceException;

}
