package com.bigroi.stock.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Product;

public interface ProductService {

	List<Product> getAllProduct() throws ServiceException;

	ModelMap tradeOffers(long id) throws ServiceException;

	ModelMap callEditProduct(long id) throws ServiceException;

	void callSaveProduct(long id, Product product) throws ServiceException;

	List<Product> getListOfProductsForAdmin(HttpSession session) throws ServiceException;

	ModelMap callEditProductForAdmin(long id) throws ServiceException;

	void callSaveProductForAdmin(long id, Product product) throws ServiceException;

	void callDeleteProduct(long id, HttpSession session) throws ServiceException;

}
