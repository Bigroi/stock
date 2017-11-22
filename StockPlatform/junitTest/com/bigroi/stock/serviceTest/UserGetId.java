package com.bigroi.stock.serviceTest;

import org.junit.Test;

import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class UserGetId {
	
	@Test
	public void getMaxId() throws ServiceException{
		System.out.println(ServiceFactory.getUserService().getUserId());
	}

}
