package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.TempKey;
import com.bigroi.stock.bean.db.UserRole;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.GenerateKeyDao;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.dao.UserRoleDao;
import com.bigroi.stock.messager.message.LinkResetPasswordMessage;
import com.bigroi.stock.messager.message.ResetUserPasswordMessage;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.Generator;

@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private GenerateKeyDao keysDao;
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private ResetUserPasswordMessage resetUserPasswordMessage;
	@Autowired
	private LinkResetPasswordMessage linkResetPasswordMessage;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void addUser(StockUser user) {
		user.getCompany().setStatus(CompanyStatus.NOT_VERIFIED);
		
		companyDao.add(user.getCompany());
		user.setCompanyId(user.getCompany().getId());
		if (user.getCompany().getCompanyAddress() != null){
			user.getCompany().getCompanyAddress().setCompanyId(user.getCompanyId());
			addressDao.addAddress(user.getCompany().getCompanyAddress());
			user.getCompany().setAddressId(user.getCompany().getCompanyAddress().getId());
			companyDao.update(user.getCompany());
		}
		userDao.add(user);
		
		List<UserRole> listRole = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UserRole userRole = new UserRole();
			userRole.setRole(Role.valueOf(grantedAuthority.getAuthority()));
			userRole.setUserId(user.getId());
			listRole.add(userRole);
		}
		userRoleDao.add(listRole);
	}

	@Override
	@Transactional
	public void update(StockUser user) {
		userDao.update(user);
		user.getCompany().setAddressId(user.getCompany().getCompanyAddress().getId());
		companyDao.update(user.getCompany());
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		StockUser user = userDao.getByUsernameWithRoles(username);
		if (user == null){
			throw new UsernameNotFoundException("label.user.not_found");
		} else {
			return user;
		}
	}
	
	@Override
	public void deleteGenerateKeys() {
		keysDao.deleteGenerateKeysByDate();
	}
	
	@Override
	@Transactional
	public void sendLinkResetPassword(String username) {
		StockUser user = userDao.getByUsernameWithRoles(username);
		if (user != null){
			TempKey key = keysDao.generateKey();
			user.setKeyId(key.getId());
			userDao.updateKeyById(user);
			Map<String,String> map = new HashMap<>();
			map.put("email", user.getUsername());
			map.put("code", key.getGeneratedKey());
			linkResetPasswordMessage.sendImediatly(map, user.getCompany().getLanguage());
		}
	}

	@Override
	@Transactional
	public boolean changePassword(String email, String code) {
		if (keysDao.checkResetKey(email, code)){
			StockUser user = userDao.getByUsernameWithRoles(email);
			String newPassword = Generator.generatePass(8);
			user.setPassword(passwordEncoder.encode(newPassword));
			userDao.updatePassword(user);
			keysDao.deleteGenerateKey(code);
			user.setPassword(newPassword);
			resetUserPasswordMessage.sendImediatly(user, user.getCompany().getLanguage());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public StockUser getByUsername(String username) {
		return userDao.getByUsernameWithRoles(username);
	}
}
