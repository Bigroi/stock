package com.bigroi.stock.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {
	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring.xml");
	
	public static UserDao getUserDao(){
		return (UserDao) CONTEX.getBean("userDao");
	}

	public static ApplicationContext getContext() {
		return CONTEX;
	}
	
	public static StubDao getStubDao(Class<?> clazz){
		return new StubDao(clazz);
	}
	
}
