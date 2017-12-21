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

	private static final String[] PAGE_LABELS_FILES = {
			"navigation.properties",
			"account.properties",
			"companies.properties",
			"login.properties",
			"lot.properties",
			"main.properties",
			"myLots.properties",
			"myDeals.properties",
			"deal.properties",
			"myTenders.properties",
			"product.properties",
			"products.properties",
			"productsForAdmin.properties",
			"registration.properties",
			"tender.properties",
			"tradeOffers.properties",
			"users.properties",
			"button.properties"
			};
	
	public static Map<String, Object> defaultLabls = new HashMap<>();
	public static Properties pageTitles = new Properties();
	
	private static final String PAGE_NAMES = "pageNames.properties";
	
	static{
		initPageNames();
		Map<String, Object> map = new HashMap<>();
		for (String fileName : PAGE_LABELS_FILES){
			map.put(fileName.split("\\.")[0], initPageLables(fileName));
		}
		defaultLabls.put("lable", map);
	}
	private static Properties initPageLables(String fileName){
		try(InputStream navigationInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)){
			BufferedReader navigationReader = new BufferedReader(new InputStreamReader(navigationInputStream, StandardCharsets.UTF_8));
			Properties properties = new Properties();
			properties.load(navigationReader);
			return properties;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected final String getLableValue(String lable){
		Object object = defaultLabls;
		for (String key : lable.split("\\.")){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)object;
			object = map.get(key);
			if (object == null){
				break;
			}
		}
		return object == null ? lable : object.toString();
	}
	
	private static void initPageNames(){
		try(InputStream pageNamesInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGE_NAMES)){
			BufferedReader pageNameReader = new BufferedReader(new InputStreamReader(pageNamesInputStream, StandardCharsets.UTF_8));
			pageTitles.load(pageNameReader);
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected final ModelAndView createModelAndView(String pageName){
		ModelAndView view = new ModelAndView(pageName, defaultLabls);
		view.addObject("page_title", pageTitles.getProperty(pageName, pageName));
		return view;
	}
}
