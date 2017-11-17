package com.bigroi.stock.service;

import java.util.List;
import java.util.Map;

import com.bigroi.stock.bean.Product;

public interface ProductService {

	List<Product> getAllProducts() throws ServiceException;

	@Deprecated
	Map<String, ?> getTradeOffers(long id) throws ServiceException;

	List<Product> getAllActiveProducts() throws ServiceException;
	
	void delete(long id, long companyId) throws ServiceException;
	
	Product getProductById(long id) throws ServiceException;
	
	void merge(Product product) throws ServiceException;

}
