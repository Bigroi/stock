package com.bigroi.stock.daotest.lot;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class LotSetStatusByProd {
	
	public static Product prod;
	
	@BeforeClass
	public static void init(){
		prod = new Product();
		prod.setId(136);
	}
	
	@Test
	public void setStatus() throws DaoException{
		DaoFactory.getLotDao().setStatusCancelByProductId(prod.getId());
	}

}
