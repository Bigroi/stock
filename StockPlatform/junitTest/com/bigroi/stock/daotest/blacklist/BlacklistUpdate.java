package com.bigroi.stock.daotest.blacklist;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class BlacklistUpdate {
	
private static Blacklist blacklist;
	
	@BeforeClass
	public static void init() throws DaoException{
		blacklist = new Blacklist();
		blacklist.setId(10);
		blacklist.setTenderId(3);
		blacklist.setLotId(3);
	}
	@Test
	public void update() throws DaoException{
		DaoFactory.getBlacklistDao().update(blacklist.getId(), blacklist);
	}
}