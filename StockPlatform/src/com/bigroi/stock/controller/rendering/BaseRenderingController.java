package com.bigroi.stock.controller.rendering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.servlet.ModelAndView;

public class BaseRenderingController {

	private static final String NAVIGATION_LABLES = "navigationLables.properties";
	public static Map<String, Object> defaultLabls = new HashMap<>();
	public static Properties pageTitles = new Properties();
	
	private static final String PAGE_NAMES = "pageNames.properties";
	
	static{
		try(InputStream navigationInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(NAVIGATION_LABLES);
			InputStream pageNamesInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGE_NAMES);
			){
			
			BufferedReader navigationReader = new BufferedReader(new InputStreamReader(navigationInputStream, StandardCharsets.UTF_8));
			Properties properties = new Properties();
			properties.load(navigationReader);
			defaultLabls.put("navigation", properties);
			
			BufferedReader pageNameReader = new BufferedReader(new InputStreamReader(pageNamesInputStream, StandardCharsets.UTF_8));
			pageTitles.load(pageNameReader);
			
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected final ModelAndView createModelAndView(String pageName){
		ModelAndView view = new ModelAndView(pageName, defaultLabls);
		view.addObject("page.title", pageTitles.getProperty(pageName, pageName));
		return view;
	}
}
