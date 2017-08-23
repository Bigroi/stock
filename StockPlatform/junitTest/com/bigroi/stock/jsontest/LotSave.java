package com.bigroi.stock.jsontest;

import java.sql.Date;
import java.text.ParseException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.controller.LotResourseController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LotSave {
	
	
	private static Lot lot;
	
		
		@BeforeClass
		public static void init(){
			
			lot = new Lot();
		//	lot.setId(1);//TODO: при такой записи lot.setPoductId(-1); в базу заносится ID -1
			lot.setDescription("test Add");
			lot.setPoductId(1);
			lot.setMinPrice(10);
			lot.setSellerId(1);
			lot.setStatus(Status.EXPIRED);
			//lot.setExpDate(new Date(lot.getExpDate().getTime()));
			lot.setVolumeOfLot(600);
		}

		@Test
		public void lotSave() throws DaoException, ParseException{
			LotResourseController res = new LotResourseController();
			String result = res.lotSave(lot.getId(), new Gson().toJson(lot), new HttpSession() {

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
					
					return null;
				}
			});
			
			ResultBean bean = new Gson().fromJson(result, ResultBean.class);
			Assert.assertEquals(bean.getResult(), 1);
		}

}
