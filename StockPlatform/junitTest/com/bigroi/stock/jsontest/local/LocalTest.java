package com.bigroi.stock.jsontest.local;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.controller.LocalizationResosurseController;
import com.google.gson.Gson;

public class LocalTest {
	
	private static String local = "en";
	
	@Test
	public void testLocal(){
		LocalizationResosurseController  res = new LocalizationResosurseController();
		List<String> list = new ArrayList<>();
		list.add("label.registration");
		list.add("label.login");
		list.add("label.account");
		list.add("label.productList");
		list.add("label.button.AddProduct");
		list.add("label.myLotList");
		list.add("label.button.AddLot");
		list.add("label.tenderList");
		list.add("label.button.AddTender");
		String result = res.getLocale(local, new Gson().toJson(list));
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}

}
