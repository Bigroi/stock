package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.StockUser;

public interface UserDao {
	
	List<StockUser> getAllUser() throws DaoException;

	StockUser getByUsername(String username) throws DaoException;
	
	void add(StockUser user)throws DaoException;
	
	boolean update(StockUser user) throws DaoException;
	
	StockUser getByUsernameWithRoles(String username) throws DaoException;

	StockUser getById(long id) throws DaoException;
	
	boolean updatePassword(StockUser user) throws DaoException;

	boolean updateForKeyId(StockUser user)throws DaoException;
}
