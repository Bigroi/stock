package com.bigroi.stock.daotest.company;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanyAdd {
	
private static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(8);
		company.setName("eee");
		company.setEmail("email");
		company.setPhone("165");
		company.setRegNumber("sdfsdf");
		company.setCountry("bel");
		company.setCity("min");
		
	}
	@Test
	public void add() throws DaoException{
		DaoFactory.getCompanyDao().add(company);
		Assert.assertNotNull(company);
	}
	
	@BeforeClass
	public static void delete() throws DaoException{
		DaoFactory.getCompanyDao().delete(company.getId());
		
	}

}
