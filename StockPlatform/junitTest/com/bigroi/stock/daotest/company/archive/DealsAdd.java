package com.bigroi.stock.daotest.company.archive;

import java.sql.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class DealsAdd {
	
	private static Deals archive;
	
	@BeforeClass
	public static void init(){
		archive = new Deals();
		archive.setId(14);
		archive.setSalerId(1);
		archive.setCustomerId(1);
		archive.setProductId(1);
		archive.setPrice(6);
		archive.setTmsTmp(new Date(archive.getTmsTmp().getTime()));	
	}
	
	@Test
	public void add() throws DaoException{
		DaoFactory.getDealsDao().add(archive);
		Assert.assertNotNull(archive);
	}
	
	@After
	public void delete() throws DaoException{
		DaoFactory.getDealsDao().delete(archive.getId());
	}

}
