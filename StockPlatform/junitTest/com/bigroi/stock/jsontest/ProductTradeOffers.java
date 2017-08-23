package com.bigroi.stock.jsontest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.controller.ProductResourseController;
import com.bigroi.stock.dao.DaoException;
import com.google.gson.Gson;

public class ProductTradeOffers {
	

//private static Product product;
private static Lot lot;
//private static Tender tender;
	
	@BeforeClass
	public static void init(){
		/*product = new Product();
		product.setId(1);*/
		lot = new Lot();
		lot.setId(1);
		/*tender = new Tender();
		tender.setId(1);
		*/
		
	}
	
	@Test
	public void  tradeOffers() throws DaoException{
		ProductResourseController res = new ProductResourseController();
		String result = res.tradeOffers(lot.getId());
		/*String result1 = res.tradeOffers(lot.getId());
		String resul2 = res.tradeOffers(tender.getId());*/
		ResultBean bean = new Gson().fromJson(result, ResultBean.class);
		Assert.assertEquals(bean.getResult(), 1);
		/*ResultBean bean1 = new Gson().fromJson(result1, ResultBean.class);
		Assert.assertEquals(bean1.getResult(), 1);
		ResultBean bean2 = new Gson().fromJson(resul2, ResultBean.class);
		Assert.assertEquals(bean2.getResult(), 1);*/
	}

}
