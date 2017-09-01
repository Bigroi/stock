package com.bigroi.stock.controller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.ResultBean;
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class LocalizationResosurseController  {
	
	private MessageSource messageSource;
	
	
	
	public MessageSource getMessageSource() {
		return messageSource;
	}



	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}



	@RequestMapping(value = "/Local.spr")
	@ResponseBody
	public String getLocale(String locale, String json){
		//ApplicationContext context = new ClassPathXmlApplicationContext();//stockPlatform-servlet.xml
		// context.getMessage(new Gson().toJson(json),new Object[]{}, new Locale(locale));
		
		messageSource.getMessage(new Gson().toJson(json),new Object[]{}, new Locale(locale));
		Map<String, Object> map = new HashMap<>();
		//ReloadableResourceBundleMessageSource messageSource = new Gson().fromJson(json, ReloadableResourceBundleMessageSource.class);
		//map.put("messageSource",messageSource.getBasenameSet());
		//map.put("contextLocal", context);
		map.put("messageLocal", messageSource);
		
		return new ResultBean(1, map).toString();
		
	}
	
	
	

}
