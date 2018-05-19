package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.Tender;

public interface TenderService {
	
	Tender getTender(long id, long companyId) throws ServiceException;
	
	List<Tender> getMyList(long companyId) throws ServiceException;
	
	void  delete(long id, long companyId) throws ServiceException;
	
	void merge(Tender tender, long companyId) throws ServiceException;

	void activate(long id, long companyId) throws ServiceException;

	List<Tender> getByProduct(int productId) throws ServiceException;
	
	void deactivate(long id, long companyId) throws ServiceException;

}
