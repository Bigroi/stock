package com.bigroi.stock.serviceTest;

import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class LotAddService {
	
	public static Lot lot;
	
	@BeforeClass
	public static void init(){
		lot = new Lot();
		lot.setId(27);
		lot.setDescription("SERVCE");
		lot.setPoductId(1);
		lot.setMinPrice(10);
		lot.setSellerId(1);
		lot.setStatus(Status.DRAFT);
		lot.setExpDate(new Date(lot.getExpDate().getTime()));
		lot.setVolumeOfLot(123);
	}
	
	@Test
	public void add() throws ServiceException{
		ServiceFactory.getLotService().addLot(lot);
	}

}
