package com.bigroi.stock.daotest.product;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductSswitchOnYes {
	
	public static Product product;
	
	@BeforeClass
	public static void init(){
		product = new Product();
		product.setId(129);
	}
	@Test
	public void switchOnYes() throws DaoException{
		DaoFactory.getProductDao().setArchived(product.getId());
	}

}
