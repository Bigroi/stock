package com.bigroi.transport.service.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.transport.bean.db.TransUser;
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
	public List<TransUser> getAll() throws ServiceException {
		try {
			return userDao.getAll();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			TransUser user = userDao.getByUsernameWithRoles(username);
			if (user == null){
				throw new UsernameNotFoundException("");//label.user.not_found
			} else {
				return user;
			}
		} catch (DaoException e) {
			throw new UsernameNotFoundException("", e);
		}
	}

}
