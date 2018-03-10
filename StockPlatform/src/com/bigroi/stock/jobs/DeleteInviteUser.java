package com.bigroi.stock.jobs;


import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DeleteInviteUser implements Runnable {

	@Override
	public void run() {
		try{
			ServiceFactory.getUserService().getInviteUsersByDate();
		}catch (ServiceException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
	}
}
