package com.bigroi.stock.daotest.tender;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderSetStatus {

	public static Company company;

	@BeforeClass
	public static void init() {
		company = new Company();
		company.setId(1);
	}

	@Test
	public void setStatus() throws DaoException {
		DaoFactory.getTenderDao().setStatusCancel(company.getId());
	}

}
