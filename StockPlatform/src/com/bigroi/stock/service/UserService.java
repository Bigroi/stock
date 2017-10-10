package com.bigroi.stock.service;

import java.sql.SQLException;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;

public interface UserService {

	void  addCompanyAndUser(Company company, User user)throws ServiceException, SQLException;

}
