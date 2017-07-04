package com.bigroi.stock.daotest.company.archive;



import java.sql.Time;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class DealsUpdate {
	
private static Deals archive;
	
	@BeforeClass
	public static void init(){
		archive = new Deals();
		archive.setId(18);
		archive.setLotId(1);
		archive.setTenderId(1);
		archive.setDealsTime(new Time(2));
	}
	@Test
	public void update() throws DaoException{
		DaoFactory.getDealsDao().update(archive.getId(), archive);
		
	}
}
