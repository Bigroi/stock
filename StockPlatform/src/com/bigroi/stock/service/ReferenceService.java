package com.bigroi.stock.service;

import com.bigroi.stock.bean.PreDeal;

public interface ReferenceService {

	PreDeal getById(long id) throws ServiceException;
	
	void setApprovedByCustomer(long id) throws ServiceException;
	
	void cancel(long preDealId) throws ServiceException;
	
	void setApprovedBySeller(long preDealId) throws ServiceException;
}
