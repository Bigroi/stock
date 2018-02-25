package com.bigroi.stock.serviceTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class InviteUserGetMaxId {
	
	
public static InviteUser inviteUser;
	
	@BeforeClass
	public static void init(){
		inviteUser = new InviteUser();
		inviteUser.setGeneratedKey("code=884Ye2IHZgYJUSZbSuFQvJjaR2AIOVbUYokza+%@1op6zAdo)q");
		
	}
	
	@Test
	public void add() throws ServiceException{
		ServiceFactory.getUserService().getInviteUserCode(inviteUser.getGeneratedKey());
		Assert.assertNotNull(inviteUser);
	}
}
