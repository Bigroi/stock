package com.bigroi.stock.daotest.tender;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderGetProductId {
	
private static Tender tender;
	
	@BeforeClass
	public static void init(){
		tender = new Tender();
		tender.setProductId(1);
	}
	@Test
	public void getByProductId() throws DaoException{
		DaoFactory.getTenderDao().getByProductId(tender.getProductId());
	}
}
