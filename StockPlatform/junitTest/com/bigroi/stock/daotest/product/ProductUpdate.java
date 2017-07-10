package com.bigroi.stock.daotest.product;


import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;

import com.bigroi.stock.dao.DaoFactory;

public class ProductUpdate {
	
private static Product product;
	
	@BeforeClass
	public static void init(){
		
		product = new Product();
		product.setId(1);
		product.setName("apple");
		product.setDescription("product");
	}
	@Test
	public void update() throws DaoException {
	DaoFactory.getProductDao().updateById(product);
	}

}
