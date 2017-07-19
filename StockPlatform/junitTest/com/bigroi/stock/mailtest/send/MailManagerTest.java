package com.bigroi.stock.mailtest.send;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;

public class MailManagerTest {
	
	private static  MailManager send;
	
	@BeforeClass
	public static void init(){
		send =  MessagerFactory.getMailManager();
	}
	
	@Test
	public  void send() throws MailManagerException{
		//send.send("mailmanager16@gmail.com", "pinyuta-p@yandex.ru", "Message subject", "content message: Hello!" );
		send.sendToAdmin("subject", "text");
	}
}
