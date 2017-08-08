package com.bigroi.stock.daotest.predeal;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.util.TmprCountEdges;

public class PreDealGetEdges {

	private static TmprCountEdges temp;

	@BeforeClass
	public static void init() {
		temp = new TmprCountEdges();
		temp.setProductId(111);
	}

	@Test
	public void getEdges() throws DaoException {
		DaoFactory.getPreDealDao().getAllEdges(temp.getProductId());
	}

}
