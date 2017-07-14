package com.bigroi.stock.daotest.tender;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderProductIdInGame {
	
	public static Tender productId;
	
	@BeforeClass
	public static void ini(){
		productId = new Tender();
		productId.setProductId(111);
	}
	@Test
	public void getProductIdInGame() throws DaoException{
		DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPrice(productId.getProductId());
	}

}
