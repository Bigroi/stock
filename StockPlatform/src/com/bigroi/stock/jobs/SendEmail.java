package com.bigroi.stock.jobs;

import org.apache.log4j.Logger;

import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class SendEmail implements Runnable {

	private static final Logger logger = Logger.getLogger(Trading.class);
	
	@Override
	public void run() {
		try {
			ServiceFactory.getMessageService().sendAllEmails();
		} catch (ServiceException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			logger.warn("unseccessfull mailing", e);
		}

	}

}
