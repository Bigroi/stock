package com.bigroi.stock.daotest.lot;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class LotProductIdInGame {
	
	public static Lot productId;
	
	@BeforeClass
	public static void init(){
		productId = new Lot();
		productId.setPoductId(1);
	}
	
	@Test
	public void getProductIdInGame() throws DaoException{
		DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(productId.getPoductId());
	}
}
