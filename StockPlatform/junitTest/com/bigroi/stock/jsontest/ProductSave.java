package com.bigroi.stock.jsontest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.controller.ProductResourseController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;

public class ProductSave {
	
private static Product product;
	
	@BeforeClass
	public static void init(){
		product = new Product();
		//product.setId(1);
		product.setName("test");
		product.setDescription("testProd");
	}
	
	@Test
	public void  productSave() throws DaoException{
		ProductResourseController res = new ProductResourseController();
		String result = res.productSave(product.getId(), new Gson().toJson(product));
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}

}
