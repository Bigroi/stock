package com.bigroi.transport.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bigroi.transport.dao.UserDao;

public class DaoFactory {

	private static final ApplicationContext CONATEXT = new ClassPathXmlApplicationContext("spring-dao.xml");
	
	public static UserDao getAllUsers(){
		return (UserDao) CONATEXT.getBean("userDao");
		
	}
}
