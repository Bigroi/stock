package com.bigroi.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class ProductResourseController extends ResourseBeanException {
	
private static final Logger logger = Logger.getLogger(ProductRenderingController.class);
	
	@RequestMapping(value = "/ProductForm.spr")
	@ResponseBody
	public String productEdit(@RequestParam("id") long id,
			@RequestParam("json") String json) throws DaoException {
		logger.info("exection ProductRenderingController.productEdit");
		logger.info(id);
		logger.info(json);
		Map<String, Object> map = new HashMap<>();
		Product product;
		if (id == -1) {
			product = new Product();
			product.setId(-1);
			logger.info("execution ProductRenderingController.productEdit - create new product");
		} else {
			product = DaoFactory.getProductDao().getById(id);
			map.put("id", product.getId());
			logger.info("execution ProductRenderingController.productEdit - get edited product");
		}		
		map.put("product", product);
		logger.info("exection ProductRenderingController.productEdit successfully finished");
		return new ResultBean(1, map).toString();                   
	}
	
	@RequestMapping(value = "/ProductSave.spr")
	@ResponseBody
	public String productSave(@RequestParam("id") long id, @RequestParam("json") String json)
			 throws DaoException {
		logger.info("exection ProductRenderingController.productSave");
		logger.info(id);
		logger.info(json);
		Product productBean = new Gson().fromJson(json, Product.class);
		Product product = new Product();  
		product.setName(productBean.getName());
		product.setDescription(productBean.getDescription());
		if (id == -1) {
			DaoFactory.getProductDao().add(productBean);
			id = productBean.getId();
			logger.info("execution ProductRenderingController.productSave - create new product");
		} else {
			product.setId(id);
			DaoFactory.getProductDao().updateById(productBean);
			logger.info("execution ProductRenderingController.productSave - update product");
		}
		logger.info("exection ProductRenderingController.productSave successfully finished");
		return new ResultBean(1,listOfProducts()).toString();
	}
	
	@RequestMapping(value = "/ProductListPage.spr")
	@ResponseBody
	public String listOfProducts() throws DaoException {
		logger.info("exection ProductRenderingController.listOfProducts");
		List<Product> product =  DaoFactory.getProductDao().getAllProduct();
		logger.info("exection ProductRenderingController.listOfProducts successfully finished");
		return new ResultBean(1, product).toString();
	}
	
	@RequestMapping(value = "/TradeOffers.spr")
	@ResponseBody
	public String tradeOffers(@RequestParam("id") long id) throws DaoException {
		logger.info("exection ProductRenderingController.tradeOffers");
		logger.info(id);
		Map<String, Object> map = new HashMap<>();
		map.put("product", DaoFactory.getProductDao().getById(id));
		map.put("listOfLots", DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(id));//TODO: listOfLots и listOfTenders приходят пустые
		map.put("listOfTenders", DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPriceDesc(id));
		logger.info("exection ProductRenderingController.tradeOffers successfully finished");
		return new ResultBean(1, map).toString();
	}

}
