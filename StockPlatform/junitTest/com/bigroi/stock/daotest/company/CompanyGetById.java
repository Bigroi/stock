package com.bigroi.stock.daotest.company;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanyGetById {
	
	private static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(1);
	}
	
	@Test
	public void getById() throws DaoException{
		DaoFactory.getCompanyDao().getById(company.getId());
	}

}
