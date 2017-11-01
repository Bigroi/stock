package com.bigroi.stock.jobs;

import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class SendEmail implements Runnable {

	@Override
	public void run() {
		try {
			ServiceFactory.getMessageService().sendAllEmails();
		} catch (ServiceException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			e.printStackTrace();
		}

	}

}
