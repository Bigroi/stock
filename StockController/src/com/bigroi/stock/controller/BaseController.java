package com.bigroi.stock.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.util.LabelUtil;

public abstract class BaseController {

	private static final String LANG_SESSION_ATTRIBUTE = "lang";
	
	@Autowired
	protected HttpSession session;
	
	@Autowired
	protected HttpServletRequest request;

	protected final void setLanguage(Locale locale){
		session.setAttribute(LANG_SESSION_ATTRIBUTE, locale);
	}
	
	protected final Locale getLanguage() {
		Locale lang = (Locale)session.getAttribute(LANG_SESSION_ATTRIBUTE);
		if (lang == null){
			lang = LabelUtil.checkLocale(request.getLocale());
			session.setAttribute(LANG_SESSION_ATTRIBUTE, lang);
		}
		return lang;
	}
	
	protected final String getSessionId(){
	    return session.getId(); 
	}
}
