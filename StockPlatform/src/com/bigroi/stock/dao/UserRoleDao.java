package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.UserRole;

public interface UserRoleDao {
	
	List<UserRole> add(List<UserRole> userRole) throws DaoException;
	
}
