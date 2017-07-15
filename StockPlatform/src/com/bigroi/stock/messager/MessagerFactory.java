package com.bigroi.stock.messager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessagerFactory {

	private static ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("spring-mail.xml");
	
	public static MailManager getMailManager(){
		return (MailManager) CONTEXT.getBean("mailManager");
	}
	
}
