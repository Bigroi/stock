package com.bigroi.stock.jobs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.service.impl.TradeServiceImpl;

@Configuration
@EnableScheduling
public class JobConfigurator {

	private static final Logger logger = Logger.getLogger(JobConfigurator.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MailManager mailManager;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private UserService userService;
	
	@Scheduled(cron="0 0 0 */2 * * ")
	public void deleteGenerateKeys() {
		try{
			userService.deleteGenerateKeys();
		}catch (Exception e) {
			mailManager.sendToAdmin(e);
		}
	}
	
	@Scheduled(cron="0 0 9,21 * * *")
	public void trade() {
		try{
			marketService.clearPreDeal();
			marketService.checkExparations();
			new TradeServiceImpl().trade();
			marketService.sendConfirmationMessages();
		}catch (Exception e) {
			mailManager.sendToAdmin(e);
			logger.warn("unsucsessful trading", e);
		}
	}
	
	@Scheduled(cron="0 */30 * * * *")
	public void sendAllEmails() {
		try {
			messageService.sendAllEmails();
		} catch (Exception e) {
			mailManager.sendToAdmin(e);
			logger.warn("unseccessfull mailing", e);
		}

	}
}
