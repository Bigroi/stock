package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.GeneratedKey;
import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.UserRole;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.InviteUserDao;
import com.bigroi.stock.dao.GenerateKeyDao;
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
	private InviteUserDao inviteUserDao;
	public GenerateKeyDao keysDao;
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
	
	public void setInviteUserDao(InviteUserDao inviteUserDao) {
		this.inviteUserDao = inviteUserDao;
	}
	
	public void setKeysDao(GenerateKeyDao keysDao) {
		this.keysDao = keysDao;
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
	public StockUser getByUsername(String username) throws ServiceException {
		try {
			return userDao.getByUsername(username);
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
	public void resetPassword(String username) throws ServiceException {
		try {
			StockUser user = userDao.getByUsername(username);
			user.setPassword(Generator.generatePass(8));
			userDao.update(user);
			Message<StockUser> message = MessagerFactory.getResetUserPasswordMessage();
			message.setDataObject(user);
			message.sendImediatly();
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			if(validate(username) == true){
				return userDao.getByUsernameWithRoles(username);
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
	@Transactional
	public void addInviteUser(InviteUser inviteUser, long id) throws ServiceException {
		try {
			inviteUser.setGeneratedKey(Generator.generateLinkKey(50));
			inviteUser.setCompanyId(id);
			inviteUserDao.add(inviteUser);
			Message<InviteUser> message = MessagerFactory.getInviteExparationMessage();
			message.setDataObject(inviteUser);
			message.sendImediatly();
		} catch (DaoException | MessageException e) {
				throw new ServiceException(e);
		}
	}

	@Override
	public InviteUser getInviteUserCode(String code) throws ServiceException {
		try {
			return inviteUserDao.getInviteUserByCode(code);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void addUserByInvite(InviteUser inviteUser, Role[] roles) throws ServiceException {
		try {
			StockUser user = new StockUser();
			user.setUsername(inviteUser.getInviteEmail());
			user.setPassword(Generator.generatePass(8));
			user.setCompanyId(inviteUser.getCompanyId());
			userDao.add(user);
			List<UserRole> listRole = new ArrayList<>();
			for (Role role : roles) {
				UserRole userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUserId(user.getId());
				listRole.add(userRole);
			}
			userRoleDao.add(listRole);
			inviteUserDao.deleteInviteUserByCode(inviteUser.getGeneratedKey());
			Message<StockUser> message = MessagerFactory.getNewPasswExparationMessage();
			message.setDataObject(user);
			message.sendImediatly();
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void getInviteUsersByDate() throws ServiceException {
		try {
			 inviteUserDao.getAllInviteUserByDate();
			 inviteUserDao.deleteInviteUsersByDate();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void sendLinkResetPassword(String username) throws ServiceException {
		try {
			StockUser user = userDao.getByUsername(username);
			user.setUsername(user.getUsername());
			GeneratedKey key = keysDao.generateKey();
			user.setKeysId(key.getId());
			userDao.updateForKeyId(user);
			Message<Map<String, String>> message = MessagerFactory.getLinkResetPasswordMessage();
			Map<String,String> map = new HashMap<>();
			map.put("email", user.getUsername());
			map.put("code", key.getGeneratedKey());
			message.setDataObject(map);
			message.sendImediatly();
		} catch (DaoException | MessageException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean checkCodeAndEmail(String email, String code) throws ServiceException {
		try {
			return keysDao.ñheckResetKey(email, code);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return false;
	}

	@Override
	@Transactional
	public void changePassword(String username) throws ServiceException {
		try {
			StockUser user = new StockUser();
			user.setUsername(username);
			user.setPassword(Generator.generatePass(8));
			userDao.updatePassword(user);
			Message<StockUser> message = MessagerFactory.getResetUserPasswordMessage();
			message.setDataObject(user);
			message.sendImediatly();
		} catch (DaoException | MessageException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
}
