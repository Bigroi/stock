package com.bigroi.stock.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.Role;

public interface UserService extends UserDetailsService{

	void addUser(Company company, StockUser user, Role[] roles)throws ServiceException;
	
	void updateCompanyAndUser(StockUser user, Company company)throws ServiceException;
	
	StockUser getByUsername(String username) throws ServiceException;
	
	Company getById(long id) throws ServiceException;
	
	void resetPassword(String username) throws ServiceException;
	
	List<StockUser> getAllUsers() throws ServiceException;
	
}
