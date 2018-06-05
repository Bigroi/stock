package com.bigroi.stock.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;

@Component
public class DeleteGenerateKeys implements Runnable {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MailManager mailManager;
	
	@Scheduled(cron="0 0 0 */2 * * ")
	public void run() {
		try{
			userService.deleteGenerateKeys();
		}catch (ServiceException e) {
			mailManager.sendToAdmin(e);
		}
	}
}
