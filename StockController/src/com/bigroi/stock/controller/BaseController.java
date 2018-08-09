package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

	@Autowired
	private HttpSession session;

	protected final String getLanguage() {
		Object lang = session.getAttribute("lang");
		return lang == null || "".equals(lang) ? "EN_US" : lang.toString().toUpperCase();
	}
	
	protected final String getSessionId(){
	    return session.getId(); 
	}
}
