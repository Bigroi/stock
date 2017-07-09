package com.bigroi.stock.daotest.deals;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class DealsAdd {
	
	private static Deals deals;
	@BeforeClass
	public static void init(){
		deals = new Deals();
		deals.setId(10);
		deals.setLotId(1);
		deals.setTenderId(1);
		deals.setDealsTime(new Date(deals.getDealsTime().getTime()));
	}
	
    @Test
	public  void add() throws DaoException{
		DaoFactory.getDealsDao().add(deals);
		Assert.assertNotNull(deals);
	}
    
    @After
	public  void delete() throws DaoException{
    	DaoFactory.getDealsDao().deletedById(deals.getId());
		
	}

}
