package com.bigroi.stock.daotest.deals;

import java.util.Date;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class DealsUpdate {
	
	private static Deals deals;
	@BeforeClass
	public static void init(){
		deals = new Deals();
		deals.setId(1);
		deals.setLotId(3);
		deals.setTenderId(3);
		deals.setDealsTime(new Date(deals.getDealsTime().getTime()));
	}
	
    @Test
	public  void update() throws DaoException{
		DaoFactory.getDealsDao().updateById(deals);
		
	}
    
   

}
