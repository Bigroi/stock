package com.bigroi.stock.daotest.tender;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderGetById {
	
private static Tender tender;
	
	@BeforeClass
	public static void init(){
		tender = new Tender();
		tender.setId(1);
	}
	
	@Test
	public void getById() throws DaoException{
		DaoFactory.getTenderDao().getById(tender.getId());
		
	}
	
	

}
