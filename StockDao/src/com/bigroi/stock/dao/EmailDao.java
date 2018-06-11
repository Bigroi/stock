package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Email;

public interface EmailDao {

	List<Email> getAll() throws DaoException;

	void add(Email email) throws DaoException;

	boolean deleteById(long id) throws DaoException;

}
