package com.bigroi.transport.service.impl;

import org.junit.Test;

import com.bigroi.transport.service.ServiceException;
import com.bigroi.transport.service.ServiceFactory;

public class UserServiceTest {

	@Test
	public void getAll() throws ServiceException{
		System.out.println(ServiceFactory.getUserService().getAll());
	}

}
