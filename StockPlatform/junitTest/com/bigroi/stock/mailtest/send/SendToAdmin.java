package com.bigroi.stock.mailtest.send;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;

public class SendToAdmin {

	private static MailManager send;

	@BeforeClass
	public static void init() {
		send = MessagerFactory.getMailManager();
	}

	@Test
	public void send() throws MailManagerException {
		try {
			throw new MailManagerException("Subject Exception");
		} catch (MailManagerException e) {
			send.sendToAdmin(e);
		}
	}
}
