package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Company;

@Service
public interface CompanyService {

	void changeStatusCompany(long id);
	
	List<Company> getAllCompanies();
	
	Company getCompanyById(long id);

	Company getByName(String name);

	Company getByRegNumber(String regNumber);
}
