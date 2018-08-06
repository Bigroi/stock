package com.bigroi.stock.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserRole;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.GenerateKeyDao;
import com.bigroi.stock.dao.UserDao;
import com.bigroi.stock.dao.UserRoleDao;
import com.bigroi.stock.messager.message.LinkResetPasswordMessage;
import com.bigroi.stock.messager.message.ResetUserPasswordMessage;
import com.bigroi.stock.service.ServiceException;
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
	public void addUserTest() throws DaoException, ServiceException{
		// given
		final long COMPANY_ID = random.nextLong();
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		company.setCompanyAddress(createObject(CompanyAddress.class));
		
		StockUser user = createObject(StockUser.class);
		user.setCompanyId(COMPANY_ID);
		user.setCompany(company);
		user.getAuthorities();// null
		
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
		//Mockito.verify(userRoleDao, Mockito.times(1)).add(listRole);
	}
}
