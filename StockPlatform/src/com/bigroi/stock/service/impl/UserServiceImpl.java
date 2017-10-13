package com.bigroi.stock.service.impl;

import org.springframework.transaction.annotation.Transactional;
import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;

import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;

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
	public void addCompanyAndUser(Company company, User user) throws ServiceException {
		try {
			companyDao.add(company);
			user.setCompanyId(company.getId());
			userDao.add(user);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

	@Override
	@Transactional
	public void updateCompanyAndUser(User user, Company company) throws ServiceException {
		try {
			userDao.updateById(user);
			companyDao.updateById(company);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
	}

	@Override
	@Transactional
	public User getByLoginAndPassword(String login, String password) throws ServiceException {
		try {
			return userDao.getByLoginAndPassword(login, password);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public User getByLogin(String login) throws ServiceException {
		try {
			return userDao.getByLogin(login);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public Company getById(long id) throws ServiceException {
		try {
			return companyDao.getById(id);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

}
