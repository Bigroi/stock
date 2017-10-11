package com.bigroi.stock.service;



public interface CompanyService {

	void statusCancelLotAndTender(long id) throws ServiceException;
	
	void changeStatusCompany(long id) throws ServiceException;
}
