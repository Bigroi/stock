package com.bigroi.stock.daotest.tender;

import java.sql.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class TenderAdd {
	
	private static Tender tender;
	
	@BeforeClass
	public static void init(){
		tender = new Tender();
		tender.setId(17);
		tender.setDescription("test");
		tender.setProductId(1);
		tender.setMaxPrice(4);
		tender.setCustomerId(1);
		tender.setStatus(Status.DRAFT);
		tender.setExpDate(new Date(tender.getExpDate().getTime()));
		
	}
	
	@Test
	public void add() throws DaoException{
		DaoFactory.getTenderDao().add(tender);
		Assert.assertNotNull(tender);
	}
	
	@After
	public void delete() throws DaoException{
		DaoFactory.getTenderDao().delete(tender.getId());
	}

}
