package com.bigroi.stock.controller.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.json.ResultBean;
import com.google.gson.Gson;

@Controller
@RequestMapping("/l18n/json")
public class LocalizationResosurseController {

	private static final String DEFAULT_LOCALE = "ru";

	@RequestMapping(value = "/Lables.spr")
	@ResponseBody
	public String Lables(
			@RequestParam String json, 
			HttpSession session) {
		Locale locale = getLocale(session);
		Map<String, String> map = new HashMap<>();
		for (Object key : new Gson().fromJson(json, List.class)) {
			String str = ResourceBundle.getBundle("messages", locale).getString(key.toString());
			map.put(key.toString(), str);
		}
		return new ResultBean(1, map).toString();
	}
	
	private Locale getLocale(HttpSession session) {
		Locale locale = (Locale) session.getAttribute("locale");
		if (locale == null){
			locale = new Locale(DEFAULT_LOCALE);
			session.setAttribute("locale", locale);
		}
		return locale;
	}

	public String getLabel(String locale, String key){
		return ResourceBundle.getBundle("messages", new Locale(locale)).getString(key);
		
	}

	@RequestMapping(value = "/changeLocale.spr")
	@ResponseBody
	public String changeLocale(
			@RequestParam("locale") String localeStr, 
			HttpSession session) {
		Locale locale = new Locale(localeStr);
		session.setAttribute("locale", locale);
		return new ResultBean(1, "locale.change.success").toString();
	}
	
}
