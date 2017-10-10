package com.bigroi.stock.serviceTest;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class UserAddService {

	public static User user;
	
	public static Company company;
	
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(22);
		company.setName("TEST!!!!!!!");
		company.setEmail("TEST!!!!!!");
		company.setPhone("TEST!!!!!!");
		company.setRegNumber("TEST");
		company.setCountry("TEST");
		company.setCity("TEST");
		company.setStatus(CompanyStatus.REVOKED);
		
		user = new User();
		user.setId(22);
		user.setLogin("TEST!!!!!!!");
		user.setPassword("!!");
		user.setCompanyId(22);
	}
	
	@Test
	public void userAdd() throws ServiceException, SQLException{
		ServiceFactory.getUserService().addCompanyAndUser(company, user);
		Assert.assertNotNull(user);
	}

}
