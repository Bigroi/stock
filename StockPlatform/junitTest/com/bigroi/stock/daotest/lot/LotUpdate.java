package com.bigroi.stock.daotest.lot;

import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class LotUpdate {
	
private static Lot lot;
	
	@BeforeClass
	public static void init(){
		lot = new Lot();
		lot.setId(4);
		lot.setDescription("evgen!!");
		lot.setPoductId(1);
		lot.setMinPrice(110);
		lot.setSellerId(1);
		lot.setStatus(Status.DRAFT);
		lot.setExpDate(new Date(lot.getExpDate().getTime()));
	}
	
	@Test
	public void update() throws DaoException{
		DaoFactory.getLotDao().updateById( lot);
	
	}
}
