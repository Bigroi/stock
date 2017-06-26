package com.bigroi.stock.daotest.lot;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class LotAdd {
	
	private static Lot lot;
	
	@BeforeClass
	public static void init(){
		lot = new Lot();
		lot.setId(24);
		lot.setDescription("evgen");
		lot.setPoductId(1);
		lot.setMinPrice(10);
		lot.setSalerId(1);
		lot.setStatus((byte) 2);
		lot.setExpDate(new java.sql.Date(lot.getExpDate().getTime()));
	}
	
	@Test
	public void add() throws DaoException{
		DaoFactory.getLotDao().add(lot);
		Assert.assertNotNull(lot);
		
	}
	
	@After
	public void delete() throws DaoException{
		DaoFactory.getLotDao().delete(lot.getId());
	}

}
