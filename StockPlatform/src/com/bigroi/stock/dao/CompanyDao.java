package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;

public interface CompanyDao {

	Company getById(long id) throws DaoException;

	void add(Company company) throws DaoException;

	boolean deletedById(long id) throws DaoException;

	boolean updateById(Company company) throws DaoException;
	
	List<Company> getAllCompany() throws DaoException;

	Company setStatusVerified(Company company);

	Company setStatusRevoked(Company company);

	Company setStatusNotVerified(Company company);

	

}
