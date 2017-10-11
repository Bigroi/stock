package com.bigroi.stock.service;


import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;

public interface UserService {

	void  addCompanyAndUser(Company company, User user)throws ServiceException;
	
	void updateCompanyAndUser(User user, Company company)throws ServiceException;
	
}
