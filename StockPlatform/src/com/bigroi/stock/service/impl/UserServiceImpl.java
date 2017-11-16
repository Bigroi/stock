package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
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
	public void addUser(Company company, StockUser user) throws ServiceException {
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
	public void updateCompanyAndUser(StockUser user, Company company) throws ServiceException {
		try {
			userDao.update(user);
			companyDao.update(company);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public StockUser checkUserByPassword(String login, String password) throws ServiceException {
		try {
			return userDao.getByLoginAndPassword(login, password);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public StockUser getByLogin(String login) throws ServiceException {
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
			StockUser user = userDao.getByLogin(login);
			user.setPassword(Generator.generatePass(8));
			userDao.update(user);
			Message<StockUser> message = MessagerFactory.getResetUserPasswordMessage();
			message.setDataObject(user);
			message.send();
		} catch (DaoException | MessageException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<StockUser> getAllUsers() throws ServiceException {
		try {
			return userDao.getAllUser();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return userDao.loadUser(username);
		} catch (UsernameNotFoundException | DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new UsernameNotFoundException("", e);
		}
		

	}

}
