package com.bigroi.stock.daotest.product;

import org.junit.Test;


import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductGetAll {
	
	
	@Test
	public void getAll() throws DaoException{
		System.out.println(DaoFactory.getProductDao().getAllProduct());
	}

}
