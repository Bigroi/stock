package com.bigroi.stock.daotest.blacklist;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class BlacklistAdd {
	
	private static Blacklist blacklist;
	
	@BeforeClass
	public static void init() throws DaoException{
		blacklist = new Blacklist();
		blacklist.setId(18);
		blacklist.setTenderId(1);
		blacklist.setLotId(1);
	}
	@Test
	public void add() throws DaoException{
		DaoFactory.getBlacklistDao().add(blacklist);
		Assert.assertNotNull(blacklist);
		
	}
	
	@After
	public void delete() throws DaoException{
		DaoFactory.getBlacklistDao().deletedById(blacklist.getId());
	}
	

}
