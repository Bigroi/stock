package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.UserRole;

public interface UserRoleDao {
	
	void add(List<UserRole> userRole) throws DaoException;
	
}
