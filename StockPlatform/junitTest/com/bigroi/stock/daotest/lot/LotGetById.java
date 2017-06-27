package com.bigroi.stock.daotest.lot;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class LotGetById {
	
private static Lot lot;
	
	@BeforeClass
	public static void init(){
		lot = new Lot();
		lot.setId(4);
		
	}
	
	@Test
	public void getById() throws DaoException{
		DaoFactory.getLotDao().getById(lot.getId());
		
	}

}
