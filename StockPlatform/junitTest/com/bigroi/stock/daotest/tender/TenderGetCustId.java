package com.bigroi.stock.daotest.tender;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderGetCustId {
	
	private static Tender tender;
	
	@BeforeClass
	public static void init(){
		tender = new Tender();
		tender.setCustomerId(1);
	}
	@Test
	public void getById() throws DaoException{
		DaoFactory.getTenderDao().getCustomerId(tender.getCustomerId());
	}
}
