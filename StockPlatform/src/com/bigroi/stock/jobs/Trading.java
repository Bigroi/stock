package com.bigroi.stock.jobs;

import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class Trading implements Runnable {
	
	@Override
	public void run() {
		try{
			ServiceFactory.getTradeService().trade();
		}catch (ServiceException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			e.printStackTrace();
		}
	}
}
