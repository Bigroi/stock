package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.User;

public interface UserDao {
	
	List<User> getAllUser() throws DaoExeptions;

	User getByLoginAndPassword(String login, String password);

	User getByLogin(String login);
}
