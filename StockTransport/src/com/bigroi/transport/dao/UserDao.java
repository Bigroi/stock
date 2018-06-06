package com.bigroi.transport.dao;

import java.util.List;

import com.bigroi.transport.bean.db.TransUser;
import com.bigroi.transport.dao.DaoException;

public interface UserDao {
	
	List<TransUser> getAll () throws DaoException;
	
	TransUser getByUsernameWithRoles(String username) throws DaoException;
 
}
