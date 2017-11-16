package com.bigroi.stock.serviceTest;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.service.ServiceFactory;

public class UserLoadTst {
	
	private static StockUser user;
	
	@BeforeClass
	public static  void init(){
		user = new StockUser();
		user.setLogin("Admin");
	}
	
	@Test
	public void loadUsesr(){
		System.out.println(ServiceFactory.getUserService().loadUserByUsername(user.getLogin()));
	}

}
