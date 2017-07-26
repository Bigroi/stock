package com.bigroi.stock.daotest.lot;

import org.junit.Test;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class GetAllInGame {
	
	@Test
	public void getAllInGame() throws DaoException{
		DaoFactory.getLotDao().getAllInGame();
	}

}
