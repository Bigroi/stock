package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.UserRole;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.dao.UserRoleDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.Generator;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private CompanyDao companyDao;
	private UserRoleDao userRoleDao;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		     + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	@Transactional
	public void addUser(Company company, StockUser user, Role[] roles) throws ServiceException {
		try {
			companyDao.add(company);
			user.setCompanyId(company.getId());
			userDao.add(user);
			List<UserRole> listRole = new ArrayList<>();
			for (Role role : roles) {
				UserRole userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUserId(user.getId());
				listRole.add(userRole);
			}
			userRoleDao.add(listRole);
		} catch (DaoException e) {
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
			throw new ServiceException(e);
		}
	}

	@Override
	public StockUser getByLogin(String login) throws ServiceException {
		try {
			return userDao.getByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Company getById(long id) throws ServiceException {
		try {
			return companyDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void resetPassword(long id) throws ServiceException {
		try {
			StockUser user = userDao.getById(id);
			user.setPassword(Generator.generatePass(8));
			userDao.update(user);
			Message<StockUser> message = MessagerFactory.getResetUserPasswordMessage();
			message.setDataObject(user);
			message.send();
		} catch (DaoException | MessageException e) {
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
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		try {
			if(validate(login) == true){
			return userDao.getByLoginWithRoles(login);
			}
			return null;
		} catch (UsernameNotFoundException | DaoException e) {
			throw new UsernameNotFoundException("", e);
		}
	}
	
	public static boolean validate(String email) {
		Pattern p = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = p.matcher(email);
		return matcher.matches();
	}

	@Override
	public StockUser getUserId() throws ServiceException {
		try {
			 return userDao.getUserId();
		} catch ( DaoException e) {
			throw new ServiceException(e);
		}
	}

}
