package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;


public interface CompanyDao {

	Company getById(long id) throws DaoException;

	void add(Company company) throws DaoException;

	boolean update(Company company) throws DaoException;
	
	List<Company> getAllCompany() throws DaoException;

	void setStatus(long companyId, CompanyStatus status) throws DaoException;

	Company getByRegNumber(String regNumber) throws DaoException;

	Company getByName(String name) throws DaoException;

}
