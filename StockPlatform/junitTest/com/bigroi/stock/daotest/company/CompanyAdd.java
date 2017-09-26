package com.bigroi.stock.daotest.company;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanyAdd {
	
private static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(12);
		company.setName("TEST");
		company.setEmail("email");
		company.setPhone("165");
		company.setRegNumber("sdfsdf");
		company.setCountry("bel");
		company.setCity("min");
        company.setStatus(CompanyStatus.VERIFIED);
		
	}
	@Test
	public void add() throws DaoException{
		DaoFactory.getCompanyDao().add(company);
		Assert.assertNotNull(company);
	}
	
	@After
	public  void delete() throws DaoException{
		//DaoFactory.getCompanyDao().deletedById(company.getId());
		
	}

}
