package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Company;

public interface CompanyService {

	void statusCancelLotAndTender(long id) throws ServiceException;
	
	void changeStatusCompany(long id) throws ServiceException;
	
	List<Company>  getAllCompsny() throws ServiceException;
}
