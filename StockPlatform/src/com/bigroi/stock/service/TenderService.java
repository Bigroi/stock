package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Tender;

public interface TenderService {
	
	Tender getTender(long id, long companyId) throws ServiceException;
	
	List<Tender> getMyList(long companyId) throws ServiceException;
	
	void  cancel(long id) throws ServiceException;
	
	void merge(Tender tender) throws ServiceException;

	void startTrading(long id) throws ServiceException;

	List<Tender> getByProduct(int productId) throws ServiceException;

}
