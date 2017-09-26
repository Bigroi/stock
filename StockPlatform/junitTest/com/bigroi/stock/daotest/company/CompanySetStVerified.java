package com.bigroi.stock.daotest.company;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoFactory;

public class CompanySetStVerified {

	public static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(10);
	}
	
	@Test
	public void setVerified(){
		DaoFactory.getCompanyDao().setStatusVerified(company);
	}
}
