package com.bigroi.stock.jsontest;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.controller.LotResourseController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;

public class LotCancel {

	private static Lot lot;
	
	
	@BeforeClass
	public static void init(){
		
		lot = new Lot();
		lot.setId(1);
		lot.setDescription("test Add");
		lot.setPoductId(1);
		lot.setMinPrice(10);
		lot.setSellerId(1);
		lot.setStatus(Status.EXPIRED);
		//lot.setExpDate(new Date(lot.getExpDate().getTime()));
		lot.setVolumeOfLot(600);
	}

	@Test
	public void lotCancel() throws DaoException, ParseException{
		LotResourseController res = new LotResourseController();
		String result = res.lotCancel(lot.getId(), new Gson().toJson(lot));
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}
	
}
