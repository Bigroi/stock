package com.bigroi.stock.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bigroi.stock.dao.template.UserTemplate;

public class AppContext {
	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring.xml");
	
	public static ApplicationContext getContext(){		
		return CONTEX;	
	}
	
	public static UserTemplate getUserDAO(){
		return (UserTemplate) CONTEX.getBean("userDAO");
		
	}
}
