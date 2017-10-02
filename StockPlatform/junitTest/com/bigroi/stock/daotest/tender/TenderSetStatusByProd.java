package com.bigroi.stock.daotest.tender;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderSetStatusByProd {
	
	public static Product prod;
	
	@BeforeClass
	public static void init(){
		prod = new Product();
		prod.setId(136);
	}
	
	@Test
	public void setStatus() throws DaoException{
		DaoFactory.getTenderDao().setStatusCancelByProductId(prod.getId());
	}

}
