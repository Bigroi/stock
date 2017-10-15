package com.bigroi.stock.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Product;

public interface ProductService {
	
	List<Product> getAllProduct() throws ServiceException;
	 
	 ModelMap tradeOffers(long id) throws ServiceException;
	 
	 ModelMap callEditProduct(long id) throws ServiceException;
	 
	 void callSaveProduct(long id, Product product) throws ServiceException;
	 
	 

}
