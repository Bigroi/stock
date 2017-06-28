package com.bigroi.stock.daotest.tender;

import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderUdate {
	
private static Tender tender;
	
	@BeforeClass
	public static void init(){
		tender = new Tender();
		tender.setId(11);
		tender.setDescription("test");
		tender.setProductId(1);
		tender.setMaxPrice(4);
		tender.setCustomerId(1);
		tender.setStatus((byte) 3);
		tender.setExpDate(new Date(tender.getExpDate().getTime()));
	}
	@Test
	public void update() throws DaoException{
		DaoFactory.getTenderDao().update(tender.getId(), tender);
	}
}
