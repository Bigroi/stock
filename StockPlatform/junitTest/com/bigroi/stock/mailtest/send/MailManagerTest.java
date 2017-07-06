package com.bigroi.stock.mailtest.send;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.common.Constant;
import com.bigroi.stock.mail.MailManager;

public class MailManagerTest {
	
	private static  MailManager send;
	
	@BeforeClass
	public static void init(){
		send =  new MailManager(Constant.EMAIL_USER,Constant.EMAIL_PASS);
	}
	@Test
	public  void send(){
		send.send(Constant.EMAIL_USER, "test", "hello world!");
	}
}
