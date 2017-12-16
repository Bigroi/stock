package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.UserRole;

public interface UserRoleDao {
	
	void add(List<UserRole> userRole) throws DaoException;
	
}
