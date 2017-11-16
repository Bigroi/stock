package com.bigroi.stock.dao;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.bigroi.stock.bean.StockUser;

public interface UserDao {
	
	List<StockUser> getAllUser() throws DaoException;

	StockUser getByLoginAndPassword(String login, String password) throws DaoException;

	StockUser getByLogin(String login) throws DaoException;
	
	void add(StockUser user)throws DaoException;
	
	boolean update(StockUser user) throws DaoException;
	
	UserDetails loadUser(String username) throws DaoException;
	
	
}
