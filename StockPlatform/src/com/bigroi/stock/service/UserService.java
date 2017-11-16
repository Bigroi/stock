package com.bigroi.stock.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;

public interface UserService extends UserDetailsService{

	void addUser(Company company, StockUser user)throws ServiceException;
	
	void updateCompanyAndUser(StockUser user, Company company)throws ServiceException;
	
	StockUser checkUserByPassword(String login, String password) throws ServiceException;
	
	StockUser getByLogin(String login) throws ServiceException;
	
	Company getById(long id) throws ServiceException;
	
	void resetPassword(String login) throws ServiceException;
	
	List<StockUser> getAllUsers() throws ServiceException;
	
}
