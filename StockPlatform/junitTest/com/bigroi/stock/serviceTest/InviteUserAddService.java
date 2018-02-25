package com.bigroi.stock.serviceTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class InviteUserAddService {
	
	public static InviteUser inviteUser;
	
	@BeforeClass
	public static void init(){
		inviteUser = new InviteUser();
		inviteUser.setId(113);
		inviteUser.setInviteEmail("test3@mail.com");
		
	}
	
	@Test
	public void add() throws ServiceException{
		//ServiceFactory.getUserService().addInviteUser(inviteUser);
		Assert.assertNotNull(inviteUser);
	}
	

}
