package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ChartTrace;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.bean.ui.TradeOffer;

public interface ProductService {

	List<Product> getAllProducts() throws ServiceException;

	List<Product> getAllActiveProducts() throws ServiceException;
	
	List<ProductForUI> getAllActiveProductsForUI() throws ServiceException;
	
	void delete(long id) throws ServiceException;
	
	Product getProductById(long id) throws ServiceException;
	
	void merge(Product product) throws ServiceException;
	
	List<TradeOffer> getTradeOffers(long productId) throws ServiceException;

	List<ChartTrace> getChartTraces(long productId) throws ServiceException;

}
