package com.bigroi.stock.jobs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.ServiceException;

@Component
public class SendEmail {

	private static final Logger logger = Logger.getLogger(Trading.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MailManager mailManager;
	
	@Scheduled(cron="0 */30 * * * *")
	public void run() {
		try {
			messageService.sendAllEmails();
		} catch (ServiceException e) {
			mailManager.sendToAdmin(e);
			logger.warn("unseccessfull mailing", e);
		}

	}

}
