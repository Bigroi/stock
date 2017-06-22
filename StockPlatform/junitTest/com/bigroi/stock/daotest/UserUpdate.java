package com.bigroi.stock.daotest;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoExeptions;
import com.bigroi.stock.dao.DaoFactory;

public class UserUpdate {
	private static User user;

	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(23);
		user.setLogin("evgen");
		user.setPassword("123");
	}
	@Test
	public void update() throws DaoExeptions {
	
	DaoFactory.getUserDao().update(user.getId(),user);
		Assert.assertNotNull(user);

	}
}
