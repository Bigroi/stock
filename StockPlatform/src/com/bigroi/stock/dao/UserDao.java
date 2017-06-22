package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.User;

public interface UserDao {
	
	List<User> getAllUser() throws DaoExeptions;

	User getByLoginAndPassword(String login, String password) throws DaoExeptions;

	User getByLogin(String login) throws DaoExeptions;
	
	void add(User user)throws DaoExeptions;
	
	void delete (Long id) throws DaoExeptions;
	
	void update(long id, User user) throws DaoExeptions;
}
