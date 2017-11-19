package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.StockUser;

public interface UserDao {
	
	List<StockUser> getAllUser() throws DaoException;

	StockUser getByLogin(String login) throws DaoException;
	
	void add(StockUser user)throws DaoException;
	
	boolean update(StockUser user) throws DaoException;
	
	StockUser getByLoginWithRoles(String login) throws DaoException;

	StockUser getById(long id) throws DaoException;
	
	
}
