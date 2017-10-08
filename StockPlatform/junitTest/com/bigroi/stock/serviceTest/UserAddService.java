package com.bigroi.stock.serviceTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class UserAddService {

	public static User user;
	
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(5);
		user.setLogin("EvgenAdmin");
		user.setPassword("123");
		user.setCompanyId(11);
	}
	
	@Test
	public void userAdd() throws ServiceException{
		ServiceFactory.getUserService().addUser(user);
		Assert.assertNotNull(user);
	}

}
