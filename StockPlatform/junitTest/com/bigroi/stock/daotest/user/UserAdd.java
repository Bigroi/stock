package com.bigroi.stock.daotest.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class UserAdd {
	
	private static User user;
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(29);
		user.setLogin("evgen");
		user.setPassword("123");
		user.setCompanyId(2);
	}
	
	@Test
	public void add() throws DaoException{
		DaoFactory.getUserDao().add(user);
		Assert.assertNotNull(user);
	}
	@After
	public void delete()throws DaoException{
		DaoFactory.getUserDao().deleteById(user.getId());
	}
}
