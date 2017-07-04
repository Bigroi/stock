package com.bigroi.stock.daotest.company.archive;


import java.sql.Time;

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
		archive.setId(16);
		archive.setLotId(1);
		archive.setTenderId(1);
		archive.setDealsTime(new Time(0));
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
