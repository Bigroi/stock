package com.bigroi.stock.daotest.product;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductGetAll {
	
	private static Product product;
	
	@BeforeClass
	public static void init(){
		product = new Product();
		product.setId(10);
		product.setName("evgen");
		product.setDescription("product");
	}
	
	@Test
	public void getAll() throws DaoException{
		DaoFactory.getProductDao().getAllProduct();
	}

}
