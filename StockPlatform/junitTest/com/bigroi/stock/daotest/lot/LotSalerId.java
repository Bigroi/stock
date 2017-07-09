package com.bigroi.stock.daotest.lot;



import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class LotSalerId {
	
private static Lot lot;
	
	@BeforeClass
	public static void init(){
		lot = new Lot();
		lot.setSellerId(1);
	}
	
	@Test
	public void getSalerId() throws DaoException{
		DaoFactory.getLotDao().getBySellerId(lot.getSellerId());
		
	}

}
