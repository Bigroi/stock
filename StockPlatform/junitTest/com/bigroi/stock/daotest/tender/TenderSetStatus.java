package com.bigroi.stock.daotest.tender;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoFactory;

public class TenderSetStatus {
	
	public static Tender tender;
	
	@BeforeClass
	public static void init(){
		tender = new Tender();
		tender.setCustomerId(1);
	}
	
	@Test
	public void setStatus(){
		DaoFactory.getTenderDao().setStatusCancel(tender);
	}

}
