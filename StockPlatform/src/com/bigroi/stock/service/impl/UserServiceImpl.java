package com.bigroi.stock.service.impl;

import java.sql.SQLException;
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
	public void addCompanyAndUser(Company company, User user) throws ServiceException, SQLException {
		try {
			companyDao.add(company);
			user.setCompanyId(company.getId());
			userDao.add(user);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

}
