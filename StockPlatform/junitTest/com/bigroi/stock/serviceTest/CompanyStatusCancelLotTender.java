package com.bigroi.stock.serviceTest;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class CompanyStatusCancelLotTender {
	
	public static Company company;
	
	@BeforeClass
	public static void init(){
		company = new Company();
		company.setId(1);
	}
	
	@Test
	public void changeStatus() throws ServiceException{
		ServiceFactory.getCompanyService().statusCancelLotAndTender(company.getId());
	}

}
