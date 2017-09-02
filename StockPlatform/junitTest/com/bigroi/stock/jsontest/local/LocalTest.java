package com.bigroi.stock.jsontest.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;

import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.controller.LocalizationResosurseController;
import com.google.gson.Gson;

public class LocalTest {
	
	private static String local = "ru";
	
	@BeforeClass
	public static void init(){
		
		
	}
	
	@Test
	public void testLocal(){
		LocalizationResosurseController  res = new LocalizationResosurseController();
		List<String> list = new ArrayList<>();
		list.add("label.registration");
		String result = res.getLocale(local, new Gson().toJson(list));
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}

}
