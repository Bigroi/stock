package com.bigroi.stock.daotest.predeal;

import org.junit.Test;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class PreDealGetEdges {
	
	@Test
	public void getEdges() throws DaoException{
		DaoFactory.getPreDealDao().getAllEdges();
	}

}
