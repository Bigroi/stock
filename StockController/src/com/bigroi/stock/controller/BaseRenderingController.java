package com.bigroi.stock.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.util.LabelUtil;


public abstract class BaseRenderingController extends BaseController{

	private static final String BUILD_FILE = "build.txt";
	
	private static final String BUILD_NUMBER;
	
	static{
		String buildNumber;
		try(InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BUILD_FILE)){
			if (stream != null){
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
				buildNumber = bufferedReader.readLine();
			} else {
				buildNumber = "local_build";
			}
		}catch (IOException e) {
			buildNumber = "local_build";
		}
		BUILD_NUMBER = buildNumber;
	}
	
	@Autowired
	protected LabelService labelService;
	
	protected final ModelAndView createModelAndView(String pageName){
		Object user = null;
		if (SecurityContextHolder.getContext() != null &&
				SecurityContextHolder.getContext().getAuthentication() != null){
			user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return new ModelAndView(pageName)
				.addObject("label", new LabelMap())
				.addObject("user", user)
				.addObject("build_number", BUILD_NUMBER)
				.addObject("languages", LabelUtil.getPassibleLanguages(getLanguage()))
				.addObject("page_title", labelService.getLabel("pageNames", pageName, getLanguage()));
	}
	
	private class LabelMap extends HashMap<String, Object>{
		
		private static final long serialVersionUID = -4893325882571654741L;
		
		private String category;
		
		public LabelMap(){}
		
		public LabelMap(String category){
			this.category = category;
		}
		
		@Override
		public Object get(Object key) {
			if (category == null){
				return new LabelMap(key.toString());
			} else {
				return labelService.getLabel(category, key.toString(), getLanguage());
			}
		}
		
		@Override
		public boolean equals(Object arg0) {
			return super.equals(arg0);
		}
		
		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

}
