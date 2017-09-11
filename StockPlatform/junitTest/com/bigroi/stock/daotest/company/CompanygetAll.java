package com.bigroi.stock.daotest.company;

import org.junit.Test;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanygetAll {
	
	@Test
	public void getAll() throws DaoException{
		DaoFactory.getCompanyDao().getAllCompany();
	}

}
