package com.bigroi.stock.daotest.predeal;

import java.sql.Date;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.PreDeal;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class PreDealUpdate {
	
private static PreDeal pred;
	
	@BeforeClass
	public static void init(){
		pred = new PreDeal();
		pred.setId(12);
		pred.setSellerHashCode("!!!");
		pred.setCustomerHashCode("evgen!!!");
		pred.setTenderId(1);
		pred.setLotId(1);
		pred.setSallerApprov(true);
		pred.setCustApprov(true);
		pred.setDealDate(new Date(pred.getDealDate().getTime()));
	}
	@Test
	public void update() throws DaoException{
		DaoFactory.getPreDealDao().update(pred.getId(), pred);
	}

}
