package com.bigroi.stock.daotest.user;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class UserGetAll {
	
	private static User user;
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(26);
		user.setLogin("evgen");
		user.setPassword("123");
	}
	@Test
	public void getAll() throws DaoException {
	
	DaoFactory.getUserDao().getAllUser();
		

	}

}
