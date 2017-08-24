package com.bigroi.stock.jsontest;

import java.sql.Date;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.controller.TenderResourseController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;

public class TenderEdit {
	
	private static User user;
	private static Tender tender;
	
	@BeforeClass
	public static void init(){
		user = new User();
		user.setId(1);
		user.setLogin("Admin");
		user.setPassword("1");
		user.setCompanyId(1);
		tender = new Tender();
		tender.setId(1);
		tender.setDescription("Javatest");
		tender.setProductId(1);
		tender.setMaxPrice(4);
		tender.setCustomerId(1);
		tender.setStatus(Status.DRAFT);
		tender.setExpDate(new Date(tender.getExpDate().getTime()));
		tender.setVolumeOfTender(500);
	
	}
	
	@Test
	public void tenderEdit() throws DaoException{
		TenderResourseController res = new TenderResourseController();
		String result = res.tenderEdit(tender.getId(), new HttpSession() {

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
		Assert.assertEquals(bean.getResult(), 1);
	}

}
	

