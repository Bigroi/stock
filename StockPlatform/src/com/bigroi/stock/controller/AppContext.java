package com.bigroi.stock.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {
	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring.xml");
	
	public static ApplicationContext getContext(){		
		return CONTEX;		
	}
}
