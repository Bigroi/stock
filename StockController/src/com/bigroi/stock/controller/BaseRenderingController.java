package com.bigroi.stock.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class BaseRenderingController {

//	private static final String[] PAGE_LABELS_FILES = {
//			};
	
	public static Map<String, Object> defaultLabels = new HashMap<>();
	public static Properties pageTitles;
	
	private static final String PAGE_NAMES = "_pageNames.properties";
	private static final String PAGE_LABELS_FILES = "_pageLabelsFiles.properties";
	
	static{
		pageTitles = initLabels(PAGE_NAMES);
		Map<String, Object> map = new HashMap<>();
		for (String fileName : getLabelsFileNames()){
			map.put(fileName.split("\\.")[0], initLabels(fileName));
		}
		defaultLabels.put("label", map);
	}
	private static Properties initLabels(String fileName){
		try(BufferedReader reader = getBufferedReader(fileName)){
			Properties properties = new Properties();
			properties.load(reader);
			return properties;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static List<String> getLabelsFileNames(){
		try(BufferedReader reader = getBufferedReader(PAGE_LABELS_FILES)){
			List<String> result = new ArrayList<>();
			while (reader.ready()){
				String name = reader.readLine();
				if (!"".equals(name) && !name.startsWith("#")){
					result.add(name);
				}
			}
			return result;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static BufferedReader getBufferedReader(String fileName){
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
	}
	
	protected final ModelAndView createModelAndView(String pageName){
		Object user = null;
		if (SecurityContextHolder.getContext() != null &&
				SecurityContextHolder.getContext().getAuthentication() != null){
			user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return new ModelAndView(pageName, defaultLabels)
				.addObject("user", user)
				.addObject("page_title", pageTitles.getProperty(pageName, pageName));
	}
	
	protected final String getLabelValue(String label){
		Object object = defaultLabels;
		for (String key : label.split("\\.")){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)object;
			object = map.get(key);
			if (object == null){
				break;
			}
		}
		return object == null ? label : object.toString();
	}
}
