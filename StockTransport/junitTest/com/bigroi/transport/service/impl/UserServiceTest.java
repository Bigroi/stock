package com.bigroi.transport.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.transport.dao.DaoException;
import com.bigroi.transport.dao.UserDao;
import com.bigroi.transport.service.ServiceException;

public class UserServiceTest {
	
	@Autowired
	UserDao user;

	@Test
	public void getAll() throws ServiceException, DaoException{
		//System.out.println(ServiceFactory.getUserService().getAll());
		//UserServiceImpl user = new UserServiceImpl();
		//UserDaoImpl user = new UserDaoImpl();
		System.out.println(user.getAll());
	}

}
