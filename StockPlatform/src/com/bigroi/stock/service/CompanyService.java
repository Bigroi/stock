package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.Company;

public interface CompanyService {

	void changeStatusCompany(long id) throws ServiceException;
	
	List<Company> getAllCompanies() throws ServiceException;
	
	Company getCompanyById(long id) throws ServiceException;

}
