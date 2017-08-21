package com.bigroi.stock.jsontest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.controller.AccountResourceController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AccountgoEditAccount {
	
	private static User user;
	private static Company company;
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setLogin("Admin");
		user.setPassword("1");
		user.setCompanyId(1);
		company = new Company();
		company.setName("Test");
		company.setEmail("@test");
		company.setPhone("123434ewr");
		company.setRegNumber("dsfdsf324324");
		company.setCountry("sdfbe");
		company.setCity("Minsk");
		company.setStatus(CompanyStatus.NOT_VERIFIED);
	}
	
	@Test
	public void gotoPage() throws DaoException{
		AccountResourceController res = new AccountResourceController();
		List<Object> listObj = new ArrayList<>();
		listObj.add(user);
		listObj.add(company);
		String result = res.editAccount(new Gson().toJson(listObj), new HttpSession() {
			//String result = res.registration(new Gson().toJson(user), new HttpSession() {

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
				
				return listObj;//user
			}
		});
	}

}
