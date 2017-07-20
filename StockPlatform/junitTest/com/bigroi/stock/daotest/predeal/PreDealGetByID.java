package com.bigroi.stock.daotest.predeal;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class PreDealGetByID {
	
private static PreDeal pred;
	
	@BeforeClass
	public static void init(){
		pred = new PreDeal();
		pred.setId(1);
	}
	@Test
	public void getByID() throws DaoException{
		DaoFactory.getPreDealDao().getById(pred.getId());
	}


}
