package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Company;

@Service
public interface CompanyService {

	void changeStatusCompany(long id) throws ServiceException;
	
	List<Company> getAllCompanies() throws ServiceException;
	
	Company getCompanyById(long id) throws ServiceException;

	Company getByName(String name) throws ServiceException;

	Company getByRegNumber(String regNumber) throws ServiceException;
}
