package com.bigroi.stock.daotest.predeal;

import java.sql.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.PreDeal;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class PreDealAdd {
	
	private static PreDeal pred;
	
	@BeforeClass
	public static void init(){
		pred = new PreDeal();
		pred.setId(1);
		pred.setSellerHashCode("aa");
		pred.setCustomerHashCode("bb");
		pred.setTenderId(1);
		pred.setLotId(1);
	    pred.setSallerApprov(Boolean.parseBoolean(pred.YES));
		pred.setCustApprov(Boolean.parseBoolean(pred.YES));
		pred.setDealDate(new Date(pred.getDealDate().getTime()));
		
	}
	@Test
	public void add() throws DaoException{
		DaoFactory.getPreDealDao().add(pred);
		Assert.assertNotNull(pred);
	}
	
	@After
	public void delete() throws DaoException{
		DaoFactory.getPreDealDao().delete(pred.getId());
	}

}
