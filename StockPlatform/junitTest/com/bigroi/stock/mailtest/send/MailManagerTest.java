package com.bigroi.stock.mailtest.send;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.mail.MailManager;

public class MailManagerTest {
	
	private static  MailManager send;
	
	@BeforeClass
	public static void init(){
		send =  new MailManager("mailmanager16@gmail.com","qwerty123321");
	}
	@Test
	public  void send(){
		send.send("mailmanager16@gmail.com", "test", "hello world!");
	}
}
