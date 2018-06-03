package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.GeneratedKey;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserRole;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
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
	private GenerateKeyDao keysDao;
	private AddressDao addressDao;
	
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	
	public void setKeysDao(GenerateKeyDao keysDao) {
		this.keysDao = keysDao;
	}

	@Override
	@Transactional
	public void addUser(StockUser user) throws ServiceException {
		try {
			user.getCompany().setStatus(CompanyStatus.NOT_VERIFIED);
			
			companyDao.add(user.getCompany());
			user.setCompanyId(user.getCompany().getId());
			user.getCompany().getAddress().setCompanyId(user.getCompanyId());;
			addressDao.addAddress(user.getCompany().getAddress());
			userDao.add(user);
			user.getCompany().setAddressId(user.getCompany().getAddress().getId());
			companyDao.update(user.getCompany());
			
			List<UserRole> listRole = new ArrayList<>();
			for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
				UserRole userRole = new UserRole();
				userRole.setRole(Role.valueOf(grantedAuthority.getAuthority()));
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
	public void update(StockUser user) throws ServiceException {
		try {
			userDao.update(user);
			user.getCompany().setAddressId(user.getCompany().getAddress().getId());
			companyDao.update(user.getCompany());
			addressDao.updateAddress(user.getCompany().getAddress());
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			StockUser user = userDao.getByUsernameWithRoles(username);
			if (user == null){
				throw new UsernameNotFoundException("label.user.not_found");
			} else {
				return user;
			}
		} catch (DaoException e) {
			throw new UsernameNotFoundException("", e);
		}
	}
	
	@Override
	public void deleteGenerateKeys() throws ServiceException {
		try {
			keysDao.deleteGenerateKeysByDate();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public void sendLinkResetPassword(String username) throws ServiceException {
		try {
			StockUser user = userDao.getByUsernameWithRoles(username);
			GeneratedKey key = keysDao.generateKey();
			user.setKeyId(key.getId());
			userDao.updateKeyById(user);
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
			StockUser user = userDao.getByUsernameWithRoles(username);
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

	@Override
	public Object getByUsername(String username) throws ServiceException {
		try {
			return userDao.getByUsernameWithRoles(username);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
}
