package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Tender;

public interface TenderService {
	
	Tender getTender(long id, long companyId) throws ServiceException;
	
	List<Tender> getMyList(long companyId) throws ServiceException;
	
	void  delete(List<Long> ids, long companyId) throws ServiceException;
	
	void merge(Tender tender, long companyId) throws ServiceException;

	void activate(List<Long> ids, long companyId) throws ServiceException;

	List<Tender> getByProduct(int productId) throws ServiceException;
	
	void deactivate(List<Long> ids, long companyId) throws ServiceException;

}
