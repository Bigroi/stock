package com.bigroi.stock.daotest.company.archive;

import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Archive;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ArchiveUpdate {
	
private static Archive archive;
	
	@BeforeClass
	public static void init(){
		archive = new Archive();
		archive.setId(1);
		archive.setSalerId(1);
		archive.setCustomerId(1);
		archive.setProductId(1);
		archive.setPrice(16);
		archive.setTmsTmp(new Date(archive.getTmsTmp().getTime()));	
	}
	
	@Test
	public void update() throws DaoException{
		DaoFactory.getArchiveDao().update(archive.getId(), archive);
		
	}
}
