package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.Generator;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private CompanyDao companyDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	@Override
	@Transactional
	public void addUser(Company company, User user) throws ServiceException {
		try {
			companyDao.add(company);
			user.setCompanyId(company.getId());
			userDao.add(user);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}

	}

	@Override
	@Transactional
	public void updateCompanyAndUser(User user, Company company) throws ServiceException {
		try {
			userDao.update(user);
			companyDao.update(company);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public User checkUserByPassword(String login, String password) throws ServiceException {
		try {
			return userDao.getByLoginAndPassword(login, password);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public User getByLogin(String login) throws ServiceException {
		try {
			return userDao.getByLogin(login);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Company getById(long id) throws ServiceException {
		try {
			return companyDao.getById(id);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void resetPassword(String login) throws ServiceException {
		try {
			User user = userDao.getByLogin(login);
			user.setPassword(Generator.generatePass(8));
			userDao.update(user);
			new Message().sendMessageChangeUserPass(user);
		} catch (DaoException | MailManagerException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<User> getAllUsers() throws ServiceException {
		try {
			return userDao.getAllUser();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

}
