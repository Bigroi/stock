package com.bigroi.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.ResultBean;
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class LocalizationResosurseController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/WelcomPageLocale.spr")
	@ResponseBody
	public String getWelcomPageLocale(String locale, String json) {
		Map<String, String> map = new HashMap<>();
		List<String> keys = new Gson().fromJson(json, List.class);
		String str = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.registration");
		map.put(keys.get(0), str);
		String str1 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.login");
		map.put(keys.get(1), str1);
		String str2 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.account");
		map.put(keys.get(2), str2);
		String str3 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.productList");
		map.put(keys.get(3), str3);
		String str4 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.button.AddProduct");
		map.put(keys.get(4), str4);
		String str5 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.myLotList");
		map.put(keys.get(5), str5);
		String str6 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.button.AddLot");
		map.put(keys.get(6), str6);
		String str7 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.tenderList");
		map.put(keys.get(7), str7);
		String str8 = ResourceBundle.getBundle("messages", new Locale(locale)).getString("label.button.AddTender");
		map.put(keys.get(8), str8);
		return new ResultBean(1, map).toString();

	}

}
