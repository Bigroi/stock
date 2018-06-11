package com.bigroi.stock.dao;


import com.bigroi.stock.bean.db.StockUser;

public interface UserDao {
	
	void add(StockUser user)throws DaoException;
	
	boolean update(StockUser user) throws DaoException;
	
	StockUser getByUsernameWithRoles(String username) throws DaoException;

	boolean updatePassword(StockUser user) throws DaoException;

	boolean updateKeyById(StockUser user)throws DaoException;
}
