package com.bigroi.stock.daotest.company;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanyUpdate {
	
private static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(2);
		company.setName("JAVATEST");
		company.setEmail("emailJAVA");
		company.setPhone("165");
		company.setRegNumber("JAVA");
		company.setCountry("JAVA");
		company.setCity("JAVA!!!!!!!!!");
//TODO		company.setStatus(Status.DRAFT);
		
	}
	
	@Test
	public void update() throws DaoException{
		DaoFactory.getCompanyDao().updateById(company);
		
	}

}
