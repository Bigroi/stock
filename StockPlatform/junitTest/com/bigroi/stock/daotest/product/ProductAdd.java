package com.bigroi.stock.daotest.product;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ProductAdd {

	private static Product product;

	@BeforeClass
	public static void init() {
		product = new Product();
		product.setId(13);
		product.setName("TEST_ARCHIVE");
		product.setDescription("product");
		product.setArchive(true);
		
	}

	@Test
	public void add() throws DaoException {
		DaoFactory.getProductDao().add(product);
		Assert.assertNotNull(product);
	}

	@After
	public void delete() throws DaoException {
		//DaoFactory.getProductDao().deletedById(product.getId());
	}
}
