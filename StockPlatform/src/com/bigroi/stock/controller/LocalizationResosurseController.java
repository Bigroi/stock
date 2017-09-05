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
	@RequestMapping(value = "/Locale.spr")
	@ResponseBody
	public String getLocale(String locale, String json) {
		Map<String, String> map = new HashMap<>();
		List<String> keys = new Gson().fromJson(json, List.class);
		for (String key : keys) {
			String str = ResourceBundle.getBundle("messages", new Locale(locale)).getString(key);
			map.put(key, str);
		}
		return new ResultBean(1, map).toString();

	}
	
	public String getLabel(String locale, String key){
		return ResourceBundle.getBundle("messages", new Locale(locale)).getString(key);
		
	}

}
