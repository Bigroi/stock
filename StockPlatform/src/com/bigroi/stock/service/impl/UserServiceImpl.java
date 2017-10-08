package com.bigroi.stock.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.bigroi.stock.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	
	
	
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}








	@Override
	@Transactional
	public void addUser(User user) throws ServiceException {
		try {
			userDao.add(user);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		
	}

	

}
