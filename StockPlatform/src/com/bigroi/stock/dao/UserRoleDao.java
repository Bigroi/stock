package com.bigroi.stock.dao;

import com.bigroi.stock.bean.UserRoles;

public interface UserRoleDao {
	
	void add(UserRoles userRole) throws DaoException;
	
}
