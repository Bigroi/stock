package com.bigroi.stock.daotest.tender;


import org.junit.Test;


import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductIdInGame {
	
	@Test
	public void getProductIdInGame() throws DaoException{
		DaoFactory.getTenderDao().getProductIdInGame();
	}

}
