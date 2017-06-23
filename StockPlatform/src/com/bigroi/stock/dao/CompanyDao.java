package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Company;

public interface CompanyDao {

	Company getById(long id) throws DaoException;

	void add(Company company) throws DaoException;

	void delete(long id) throws DaoException;

	void update(long id, Company company) throws DaoException;

}
