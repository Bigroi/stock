package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Company;

public interface CompanyDao {

	Company getById(long id) throws DaoException;

	void add(Company company) throws DaoException;

	boolean deletedById(long id) throws DaoException;

	boolean updateById(Company company) throws DaoException;

}
