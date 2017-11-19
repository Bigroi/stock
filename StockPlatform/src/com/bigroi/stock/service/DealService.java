package com.bigroi.stock.service;

import com.bigroi.stock.bean.PreDeal;

public interface DealService {

	PreDeal getById(long id) throws ServiceException;
	
	void setApprovedByCustomer(long id) throws ServiceException;
	
	void setApprovedBySeller(long preDealId) throws ServiceException;

	void cancel(long preDealId, boolean canceledBySeller) throws ServiceException;
}
