package com.bigroi.stock.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Product;

public interface ProductService {
	
	List<Product> getAllProduct() throws ServiceException;
	
	 void addProduct (Product product) throws ServiceException;
	 
	 void updateProduct (Product product) throws ServiceException;
	 
	 Product getById(long id) throws ServiceException;
	 
	 ModelMap tradeOffers(long id) throws ServiceException;

}
