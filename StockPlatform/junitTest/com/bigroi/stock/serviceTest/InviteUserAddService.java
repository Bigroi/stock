package com.bigroi.stock.serviceTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class InviteUserAddService {
	
	public static InviteUser inviteUser;
	public static Company company;
	
	@BeforeClass
	public static void init(){
		inviteUser = new InviteUser();
		for(int i = 0; i < 10; i++) inviteUser.setId(i);
		inviteUser.setInviteEmail("test3@mail.com");
		
		company = new Company();
		company.setId(1);
		
	}
	
	@Test
	public void add() throws ServiceException{
		for(int i = 0; i < 10; i++){
		ServiceFactory.getUserService().addInviteUser(inviteUser,company.getId());
		Assert.assertNotNull(inviteUser);
		}
	}
	

}
