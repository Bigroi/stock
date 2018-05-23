package com.bigroi.stock.serviceTest;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class AddressGetById {
	
	private static Address address;
	
	@BeforeClass
	public static void init(){
		address = new Address();
		address.setId(3);
	}
	
	@Test
	public void getById() throws ServiceException{
		System.out.println(ServiceFactory.getAddressService().getAddressById(address.getId()));
	}

}
