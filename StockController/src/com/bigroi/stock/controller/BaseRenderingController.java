package com.bigroi.stock.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.LabelService;


public abstract class BaseRenderingController extends BaseController{

	@Autowired
	private LabelService labelService;
	
	protected final ModelAndView createModelAndView(String pageName){
		Object user = null;
		if (SecurityContextHolder.getContext() != null &&
				SecurityContextHolder.getContext().getAuthentication() != null){
			user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return new ModelAndView(pageName)
				.addObject("label", new LabelMap())
				.addObject("user", user)
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
	}

}
