package com.bigroi.transport.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {

	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring-service.xml");
	
	public static UserService getUserService(){
		return (UserService) CONTEX.getBean("userService");
	}

}
