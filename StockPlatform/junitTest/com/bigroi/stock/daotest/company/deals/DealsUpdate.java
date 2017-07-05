package com.bigroi.stock.daotest.company.deals;



import java.sql.Date;


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
		archive.setId(1);
		archive.setLotId(5);
		archive.setTenderId(1);
		archive.setDealsTime(new Date(archive.getDealsTime().getTime()));
	}
	@Test
	public void update() throws DaoException{
		DaoFactory.getDealsDao().update(archive.getId(), archive);
		
	}
}
