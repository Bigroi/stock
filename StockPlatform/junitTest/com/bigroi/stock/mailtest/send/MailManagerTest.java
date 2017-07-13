package com.bigroi.stock.mailtest.send;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.common.Constant;
import com.bigroi.stock.mail.MailManager;
import com.bigroi.stock.mail.PropertiesMail;

public class MailManagerTest {
	
	private static  MailManager send;
	
	@BeforeClass
	public static void init(){
		send =  new MailManager(Constant.EMAIL_USER,Constant.EMAIL_PASS);
	}
	@Test
	public  void send(){
		//PropertiesMail.getContext().send("support@bigroi.com", Constant.EMAIL_USER, "Message subject", "content message: Hello!" );
		//send.send("support@bigroi.com", Constant.EMAIL_USER, "Message subject", "content message: Hello!" );
	}
}
