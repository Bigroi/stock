package com.bigroi.stock.daotest.company;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class CompanyUpdateStatus {
	
	public static Company company;
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(10);
		company.setStatus(CompanyStatus.VERIFIED);
		
	}
	
	@Test
	public void update() throws DaoException{
		DaoFactory.getCompanyDao().updateStatus(company, company.getId());
		
	}

}
