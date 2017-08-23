package com.bigroi.stock.jsontest;

import org.junit.Assert;
import org.junit.Test;


import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.controller.ProductResourseController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;

public class ProductListOfProducts {
	
	@Test
	public void  productSave() throws DaoException{
		ProductResourseController res = new ProductResourseController();
		String result = res.listOfProducts();
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
	}

}
