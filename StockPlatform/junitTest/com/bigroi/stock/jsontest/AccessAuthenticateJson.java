package com.bigroi.stock.jsontest;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.controller.AccessResourceController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;

public class  AccessAuthenticateJson {
	
	private static User user;
	
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setLogin("Admin");
		user.setPassword("1");
		
	}
	
	@Test
	public void auth() throws DaoException{
		AccessResourceController res = new AccessResourceController();
		String result = res.authenticate(new Gson().toJson(user), new HttpSession() {
			
			@Override
			public void setMaxInactiveInterval(int arg0) {
			}
			
			@Override
			public void setAttribute(String arg0, Object arg1) {
			}
			
			@Override
			public void removeValue(String arg0) {
			}
			
			@Override
			public void removeAttribute(String arg0) {
			}
			
			@Override
			public void putValue(String arg0, Object arg1) {
			}
			
			@Override
			public boolean isNew() {
				return false;
			}
			
			@Override
			public void invalidate() {
			}
			
			@Override
			public String[] getValueNames() {
				return null;
			}
			
			@Override
			public Object getValue(String arg0) {
				return null;
			}
			
			@Override
			public HttpSessionContext getSessionContext() {
				return null;
			}
			
			@Override
			public ServletContext getServletContext() {
				return null;
			}
			
			@Override
			public int getMaxInactiveInterval() {
				return 0;
			}
			
			@Override
			public long getLastAccessedTime() {
				return 0;
			}
			
			@Override
			public String getId() {
				return null;
			}
			
			@Override
			public long getCreationTime() {
				return 0;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0) {
				return user;
			}
		});
		
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		org.junit.Assert.assertEquals(bean.getResult(), 1);
		
		
	}
	

}
