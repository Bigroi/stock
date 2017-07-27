package com.bigroi.stock.daotest.email;

import org.junit.Test;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class EmailGetALL {
	
	@Test
	public void getAll() throws DaoException{
		DaoFactory.getEmailDao().getAll();
	}

}
