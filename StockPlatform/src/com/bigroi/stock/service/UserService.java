package com.bigroi.stock.service;


import java.util.List;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;

public interface UserService {

	void addUser(Company company, User user)throws ServiceException;
	
	void updateCompanyAndUser(User user, Company company)throws ServiceException;
	
	User checkUserByPassword(String login, String password) throws ServiceException;
	
	User getByLogin(String login) throws ServiceException;
	
	Company getById(long id) throws ServiceException;
	
	void resetPassword(String login) throws ServiceException;
	
	List<User> getAllUsers() throws ServiceException;
	
}
