package com.bigroi.transport.dao;

import java.util.List;

import com.bigroi.transport.bean.User;
import com.bigroi.transport.dao.DaoException;

public interface UserDao {
	
	List<User> getAll () throws DaoException;
 
}
