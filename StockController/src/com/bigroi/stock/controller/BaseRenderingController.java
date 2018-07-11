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

	private static final Map<String, Object> defaultLabels = new HashMap<>();
	private static final Properties pageTitles;
	private static final Properties devProperties;
	
	private static final String PAGE_NAMES = "_pageNames.properties";
	private static final String PAGE_LABELS_FILES = "_pageLabelsFiles.properties";
	private static final String DEV_PROPERTIES_FILES = "dev.properties";
	
	static{
		try{
			devProperties = readProperties(DEV_PROPERTIES_FILES);
			pageTitles = readProperties(PAGE_NAMES);
			Map<String, Object> map = new HashMap<>();
			for (String fileName : getLabelsFileNames()){
				map.put(fileName.split("\\.")[0], readProperties(fileName));
			}
			defaultLabels.put("label", map);
		}catch (IOException e) {
			throw new InitException(e);
		}
	}
	
	private static Properties readProperties(String fileName) throws IOException{
		try(BufferedReader reader = getBufferedReader(fileName)){
			Properties properties = new LabelMap();
			properties.load(reader);
			return properties;
		}
	}
	
	private static List<String> getLabelsFileNames() throws IOException{
		try(BufferedReader reader = getBufferedReader(PAGE_LABELS_FILES)){
			List<String> result = new ArrayList<>();
			while (reader.ready()){
				String name = reader.readLine();
				if (!"".equals(name) && !name.startsWith("#")){
					result.add(name);
				}
			}
			return result;
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
				.addObject("devProperties", devProperties)
				.addObject("page_title", pageTitles.getProperty(pageName, pageName));
	}
	
	protected final String getLabelValue(String label){
		Object object = defaultLabels;
		for (String key : label.split("\\.")){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)object;
			object = map.get(key);
		}
		return object.toString();
	}
	
	private static class LabelMap extends Properties{
		
		private static final long serialVersionUID = 6935680532239842933L;

		@Override
		public synchronized Object get(Object key) {
			Object obj = super.get(key);
			if (obj == null){
				throw new InitException("Can not find label " + key);
			}
			return obj;
		}
	}
	
	private static class InitException extends RuntimeException{

		private static final long serialVersionUID = -8330666930298807296L;

		public InitException(Throwable cause) {
			super(cause);
		}
		
		public InitException(String cause) {
			super(cause);
		}
		
	}
}
