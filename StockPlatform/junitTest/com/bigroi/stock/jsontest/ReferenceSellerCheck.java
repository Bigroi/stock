package com.bigroi.stock.jsontest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.controller.ReferenceHandlingResourse;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.messager.MailManagerException;
import com.google.gson.Gson;

public class ReferenceSellerCheck {
	
	private static PreDeal predeal;
	private static Action action;  
	
	@BeforeClass
	public static void init(){
		predeal = new PreDeal();
		predeal.setId(4);
		predeal.setSellerHashCode("xcvb");
		predeal.setCustomerHashCode("cc");
		predeal.setTenderId(3);
		predeal.setLotId(3);
		predeal.setSellerApprov("N");
		predeal.setCustApprov("N");
		
		 action = Action.CANCEL;
		
		
		
	}
	
	@Test
	public void sellerCheck() throws DaoException, IOException, MailManagerException{
		ReferenceHandlingResourse res = new ReferenceHandlingResourse();
		String result = res.sellerCheck(predeal.getId(), new Gson().toJson(predeal), predeal.getSellerHashCode(), action);
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}

}
