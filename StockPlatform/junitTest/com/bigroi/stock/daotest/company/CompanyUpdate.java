package com.bigroi.stock.daotest.company;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanyUpdate {
	
private static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(13);
		company.setName("JAVA");
		company.setEmail("emailJAVA");
		company.setPhone(165);
		company.setRegNumber("JAVA");
		company.setCountry("JAVA");
		company.setCity("JAVA");
		company.setUserId(20);
	}
	
	@Test
	public void update() throws DaoException{
		DaoFactory.getCompanyDao().update(company.getId(), company);
		
	}

}
