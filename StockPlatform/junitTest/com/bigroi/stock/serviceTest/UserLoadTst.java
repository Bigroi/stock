package com.bigroi.stock.serviceTest;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.service.ServiceFactory;

public class UserLoadTst {
	
	private static User user;
	
	@BeforeClass
	public static  void init(){
		user = new User();
		user.setLogin("Admin");
	}
	
	@Test
	public void loadUsesr(){
		System.out.println(ServiceFactory.getUserService().loadUserByUsername(user.getLogin()));
	}

}
