package com.bigroi.stock.daotest.product;

import org.junit.Test;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductGetYesAndNo {
	
	@Test
	public void getProd() throws DaoException{
		System.out.println(DaoFactory.getProductDao().getArchiveYesProduct());
		System.out.println("------------------------------------------------");
		System.out.println(DaoFactory.getProductDao().getArchiveNoProduct());
	}

}
