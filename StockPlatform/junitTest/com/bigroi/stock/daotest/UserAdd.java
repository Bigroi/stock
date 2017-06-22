package com.bigroi.stock.daotest;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoExeptions;
import com.bigroi.stock.dao.DaoFactory;

public class UserAdd {
	
	private static User user;
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(22);
		user.setLogin("evgen");
		user.setPassword("123");
	}
	
	@Test
	public void add() throws DaoExeptions{
		DaoFactory.getUserDao().add(user);
		Assert.assertNotNull(user);
	}
	@After
	public void delete() throws DaoExeptions{
		DaoFactory.getUserDao().delete(user.getId());
	}
}
