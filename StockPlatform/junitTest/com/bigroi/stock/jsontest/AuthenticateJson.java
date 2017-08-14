package com.bigroi.stock.jsontest;

import javax.servlet.http.HttpSession;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;

public class AuthenticateJson {
	
	private static User user;
	private static HttpSession session;
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setLogin("Admin");
		user.setPassword("1");
	}
	
	@Test
	public void auth() throws DaoException{
		/*Resource res = new Resource ();
		res.authenticate(user.getLogin(), user.getLogin(), session);*/
	}
	

}
