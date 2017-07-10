package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.User;

public interface UserDao {
	
	List<User> getAllUser() throws DaoException;

	User getByLoginAndPassword(String login, String password) throws DaoException;

	User getByLogin(String login) throws DaoException;
	
	void add(User user)throws DaoException;
	
	boolean deleteById (long id) throws DaoException;
	
	boolean updateById(User user) throws DaoException;
}
