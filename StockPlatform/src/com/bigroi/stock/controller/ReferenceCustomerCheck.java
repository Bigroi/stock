package com.bigroi.stock.controller;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.messager.MailManagerException;
import com.google.gson.Gson;

public class ReferenceCustomerCheck {
	
	private static PreDeal predeal;
	private static Action action;  
	
	@BeforeClass
	public static void init(){
		predeal = new PreDeal();
		predeal.setId(5);
		predeal.setSellerHashCode("bbbb");
		predeal.setCustomerHashCode("cc");
		predeal.setTenderId(11);
		predeal.setLotId(4);
		predeal.setSellerApprov("Y");
		predeal.setCustApprov("Y");
		
		 action = Action.APPROVE;
		
		
		
	}
	
	@Test
	public void sellerCheck() throws DaoException, IOException, MailManagerException{
		ReferenceHandlingResourse res = new ReferenceHandlingResourse();
		String result = res.customerCheck(predeal.getId(), new Gson().toJson(predeal), predeal.getCustomerHashCode(), action);
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}

}
