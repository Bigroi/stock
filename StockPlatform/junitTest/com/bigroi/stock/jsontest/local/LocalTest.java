package com.bigroi.stock.jsontest.local;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;

import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.controller.LocalizationResosurseController;
import com.google.gson.Gson;

public class LocalTest {
	
	private static MessageSource messageSource;
	private static String local;
	
	@BeforeClass
	public static void init(){
		
		
	}
	
	@Test
	public void testLocal(){
		/*LocalizationResosurseController  res = new LocalizationResosurseController();
		String result = res.getLocale(local, new Gson().toJson(messageSource));
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);*/
	}

}
