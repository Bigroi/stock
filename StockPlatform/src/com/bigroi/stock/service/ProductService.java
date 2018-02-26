package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.TradeOffer;

public interface ProductService {

	List<Product> getAllProducts() throws ServiceException;

	List<Product> getAllActiveProducts() throws ServiceException;
	
	void delete(long id) throws ServiceException;
	
	Product getProductById(long id) throws ServiceException;
	
	void merge(Product product) throws ServiceException;
	
	List<TradeOffer> getTradeOffers(long productId) throws ServiceException;

}
