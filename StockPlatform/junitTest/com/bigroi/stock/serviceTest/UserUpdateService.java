package com.bigroi.stock.serviceTest;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class UserUpdateService {
	
public static StockUser user;
	
	public static Company company;
	
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(22);
		company.setName("updROLBACK");
		company.setEmail("updatROLBACK!!!!!!!!!!");
		company.setPhone("upROLBACK");
		company.setRegNumber("ROLBACK");
		company.setCountry("TEST");
		company.setCity("TEST");
		company.setStatus(CompanyStatus.VERIFIED);
		
		user = new StockUser();
		user.setId(22);
		user.setLogin("ROLBACK");
		user.setPassword("ROLBACK");
		user.setCompanyId(22);
	}
	
	@Test
	public void userAdd() throws ServiceException, SQLException{
		ServiceFactory.getUserService().updateCompanyAndUser(user, company);
		Assert.assertNotNull(user);
	}

}
