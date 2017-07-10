package com.bigroi.stock.daotest.user;



import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class UserUpdate {
	private static User user;

	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(28);
		user.setLogin("Java!!");
		user.setPassword("123");
		user.setCompanyId(2);
	}
	@Test
	public void update() throws DaoException {
	
	DaoFactory.getUserDao().updateById(user);
	}
}
