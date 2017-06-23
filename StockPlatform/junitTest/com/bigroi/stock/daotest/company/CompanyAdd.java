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
		company.setId(4);
		company.setName("eee");
		company.setEmail("email");
		company.setPhone(165);
		company.setRegNumber("sdfsdf");
		company.setCountry("bel");
		company.setCity("min");
		company.setUserId(20);
	}
	
	@Test
	public void delete() throws DaoException{
		DaoFactory.getCompanyDao().delete(company.getId());
		
	}

}
