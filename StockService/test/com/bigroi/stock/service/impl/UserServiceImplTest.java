package com.bigroi.stock.service.impl;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
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
import com.bigroi.stock.util.BaseTest;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends BaseTest {
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserDao userDao;
	@Mock
	private CompanyDao companyDao;
	@Mock
	private UserRoleDao userRoleDao;
	@Mock
	private GenerateKeyDao keysDao;
	@Mock
	private AddressDao addressDao;
	
	@Mock
	private ResetUserPasswordMessage resetUserPasswordMessage;
	@Mock
	private LinkResetPasswordMessage linkResetPasswordMessage;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void addUserTest(){
		// given
		final long COMPANY_ID = random.nextLong();
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		company.setCompanyAddress(createObject(CompanyAddress.class));
		
		StockUser user = createObject(StockUser.class);
		user.setCompanyId(COMPANY_ID);
		user.setCompany(company);
		user.addAuthority(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
		
		List<UserRole> listRole = ImmutableList.of(createObject(UserRole.class));
		// mock
		Mockito.doAnswer(a -> {
								((Company)a.getArguments()[0]).setId(random.nextLong()); 
									return null;
								})
								.when(companyDao).add(user.getCompany());
		Mockito.doAnswer(a -> {
								((CompanyAddress)a.getArguments()[0]).setId(random.nextLong()); 
									return null;
								})
								.when(addressDao).addAddress(user.getCompany().getCompanyAddress());
		Mockito.when(companyDao.update(user.getCompany())).thenReturn(true);
		Mockito.doAnswer(a -> {
								((StockUser)a.getArguments()[0]).setId(random.nextLong()); 
									return null;
								})
								.when(userDao).add(user);
		Mockito.doNothing().when(userRoleDao).add(listRole);
		// when
		userService.addUser(user);
		// then
		Assert.assertEquals(user.getCompany().getStatus(), CompanyStatus.NOT_VERIFIED);
		Assert.assertNotEquals(user.getCompany().getCompanyAddress(), null);
		Mockito.verify(companyDao, Mockito.times(1)).add(user.getCompany());
		Mockito.verify(addressDao, Mockito.times(1)).addAddress(user.getCompany().getCompanyAddress());
		Mockito.verify(companyDao, Mockito.times(1)).update(user.getCompany());
		Mockito.verify(userDao, Mockito.times(1)).add(user);
		Mockito.verify(userRoleDao, Mockito.times(1)).add(Mockito.any());
	}
	
	@Test
	public void updateTest(){
		// given
		Company company = createObject(Company.class);
		company.setCompanyAddress(createObject(CompanyAddress.class));
		
		StockUser user = createObject(StockUser.class);
		user.setCompany(company);
		// mock 
		Mockito.when(userDao.update(user)).thenReturn(true);
		Mockito.when(companyDao.update(user.getCompany())).thenReturn(true);
		// when
		userService.update(user);
		// then
		Assert.assertNotEquals(user.getCompany().getCompanyAddress().getId(), null);
		Mockito.verify(userDao, Mockito.times(1)).update(user);
		Mockito.verify(companyDao, Mockito.times(1)).update(user.getCompany());
	}
	
	@Test
	public void loadUserByUsernameTest() {
		// given
		final String USER_NAME = randomString();
		
		StockUser user = createObject(StockUser.class);
		user.setUsername(USER_NAME);
		// mock
		Mockito.when(userDao.getByUsernameWithRoles(USER_NAME)).thenReturn(user);
		// when
		userService.loadUserByUsername(USER_NAME);
		// then
		Assert.assertNotEquals(user, null);
		Mockito.verify(userDao, Mockito.times(1)).getByUsernameWithRoles(USER_NAME);
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsernameThrowExceptionTest(){
		// given
		final String USER_NAME = randomString();
		// when
		userService.loadUserByUsername(USER_NAME);
	}
	
	@Test
	public void deleteGenerateKeysTest(){
		// mock
		Mockito.doNothing().when(keysDao).deleteGenerateKeysByDate();
		// when
		userService.deleteGenerateKeys();
		// then
		Mockito.verify(keysDao, Mockito.times(1)).deleteGenerateKeysByDate();
	}
	
	@Test
	public void sendLinkResetPasswordTest() {
		// given
		final String USER_NAME = randomString();
				
		StockUser user = createObject(StockUser.class);
		user.setUsername(USER_NAME);
		
		TempKey key = createObject(TempKey.class);
		// mock
		Mockito.when(userDao.getByUsernameWithRoles(USER_NAME)).thenReturn(user);
		Mockito.when(keysDao.generateKey()).thenReturn(key);
		Mockito.when(userDao.updateKeyById(user)).thenReturn(true);
		Mockito.doNothing().when(linkResetPasswordMessage).sendImediatly(new HashMap<>());
		// when
		userService.sendLinkResetPassword(USER_NAME);
		// then
		Assert.assertNotEquals(user, null);
		Assert.assertEquals(user.getKeyId(), key.getId());
		Mockito.verify(userDao, Mockito.times(1)).getByUsernameWithRoles(USER_NAME);
		Mockito.verify(userDao, Mockito.times(1)).updateKeyById(user);
		Mockito.verify(keysDao, Mockito.times(1)).generateKey();
		Mockito.verify(linkResetPasswordMessage, Mockito.times(1)).sendImediatly(Mockito.any());
	}
	
	@Test
	public void changePasswordTest(){
		// given
		final String USER_NAME = randomString();
		final String CODE = randomString();
		
		StockUser user = createObject(StockUser.class);
		user.setUsername(USER_NAME);
		// mock
		Mockito.when(keysDao.checkResetKey(USER_NAME, CODE)).thenReturn(true);
		Mockito.when(userDao.getByUsernameWithRoles(USER_NAME)).thenReturn(user);
		Mockito.when(userDao.updatePassword(user)).thenReturn(true);
		Mockito.doNothing().when(keysDao).deleteGenerateKey(CODE);
		Mockito.doNothing().when(resetUserPasswordMessage).sendImediatly(user);
		// when
		boolean bool = userService.changePassword(USER_NAME, CODE);
		// then
		Assert.assertEquals(bool, true);
		Assert.assertNotEquals(user.getPassword(), null);
		Mockito.verify(keysDao, Mockito.times(1)).checkResetKey(USER_NAME, CODE);
		Mockito.verify(userDao, Mockito.times(1)).getByUsernameWithRoles(USER_NAME);
		Mockito.verify(userDao, Mockito.times(1)).updatePassword(user);
		Mockito.verify(keysDao, Mockito.times(1)).deleteGenerateKey(CODE);
		Mockito.verify(resetUserPasswordMessage, Mockito.times(1)).sendImediatly(user);
	}
	
	@Test
	public void notChangePasswordTest() {
		// given
		final String USER_NAME = null;
		final String CODE = null;
		// mock
		Mockito.when(keysDao.checkResetKey(USER_NAME, CODE)).thenReturn(false);
		// when
		boolean bool = userService.changePassword(USER_NAME, CODE);
		// then
		Assert.assertEquals(bool, false);
		Mockito.verify(keysDao, Mockito.times(1)).checkResetKey(USER_NAME, CODE);
	}
	
	@Test
	public void getByUsernameTest(){
		// given
		final String USER_NAME = randomString();
		
		StockUser user = createObject(StockUser.class);
		// mock
		Mockito.when(userDao.getByUsernameWithRoles(USER_NAME)).thenReturn(user);
		// when
		StockUser userExpected = userService.getByUsername(USER_NAME);
		Assert.assertEquals(user, userExpected);
		Mockito.verify(userDao, Mockito.times(1)).getByUsernameWithRoles(USER_NAME);
		
	}
}
