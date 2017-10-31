package com.bigroi.stock.jsontest.local;


import org.junit.Test;

import com.bigroi.stock.controller.resource.LocalizationResosurseController;


public class GetLabel {
	
	private static String locale = "en";
	private static String key = "label.registration";
	
	@Test
	public  void getLabel(){
		LocalizationResosurseController  res = new LocalizationResosurseController();
		String result = res.getLabel(locale, key);
		System.out.println(result);
	}

}
