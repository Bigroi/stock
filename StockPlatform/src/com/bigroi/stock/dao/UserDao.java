package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.util.TmprUserLoad;

public interface UserDao {
	
	List<User> getAllUser() throws DaoException;

	User getByLoginAndPassword(String login, String password) throws DaoException;

	User getByLogin(String login) throws DaoException;
	
	void add(User user)throws DaoException;
	
	boolean update(User user) throws DaoException;
	
	List<TmprUserLoad> loadUser() throws DaoException;
}
