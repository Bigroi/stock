package com.bigroi.stock.mail;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bigroi.stock.bean.Mail;

public class PropertiesMail {

	private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("spring-mail.xml");
	public Properties properties;
	// private Mail smtp;
	// private Mail port;

	public PropertiesMail() {
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com"); // "smtp.gmail.com"
		properties.put("mail.smtp.port", "587");// port TLS "587"
		properties.put("mail.mime.charset", "UTF-8");
	}

	/*public static MailPost getContext() {
		return (MailPost) CONTEXT.getBean("mail");
	}*/
//TODO: разобраться с ошибкой в пропертях, Переписать бин Mail на Smtp или наооборот
}
