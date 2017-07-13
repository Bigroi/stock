package com.bigroi.stock.daotest.product;


import org.junit.Test;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductGetProductId {
	
	@Test
	public void getAllProductId() throws DaoException{
		DaoFactory.getProductDao().getAllProductIdInGame();
	}

}
