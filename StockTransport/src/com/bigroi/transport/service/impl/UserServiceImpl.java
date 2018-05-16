package com.bigroi.transport.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.transport.bean.User;
import com.bigroi.transport.dao.DaoException;
import com.bigroi.transport.dao.UserDao;
import com.bigroi.transport.service.ServiceException;
import com.bigroi.transport.service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public List<User> getAll() throws ServiceException {
		try {
			return userDao.getAll();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
