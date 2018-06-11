package com.bigroi.stock.jobs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TradeService;

@Component
public class Trading {
	
	private static final Logger logger = Logger.getLogger(Trading.class);
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private MailManager mailManager;
	
	@Scheduled(cron="0 0 9,21 * * *")
	public void run() {
		try{
			marketService.clearPreDeal();
			marketService.checkExparations();
			tradeService.trade();
			marketService.sendConfirmationMessages();
		}catch (ServiceException e) {
			mailManager.sendToAdmin(e);
			logger.warn("unsucsessful trading", e);
		}
	}
}
