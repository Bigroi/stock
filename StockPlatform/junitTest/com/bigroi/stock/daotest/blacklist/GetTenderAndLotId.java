package com.bigroi.stock.daotest.blacklist;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class GetTenderAndLotId {
	
	private static Blacklist blacklist;
	
	@BeforeClass
	public static void init(){
		blacklist = new Blacklist();
		blacklist.setId(1);
		blacklist.setLotId(1);
		blacklist.setTenderId(1);
	}
	
	@Test
	public void getTenderAndLotId() throws DaoException{
		DaoFactory.getBlacklistDao().getTenderIdAndLotId(blacklist.getTenderId(), blacklist.getLotId());
	}

}
