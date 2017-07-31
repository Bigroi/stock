package com.bigroi.stock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class ProductRenderingController {
	
	@RequestMapping("/ProductForm.spr")
	public ModelAndView productEdit(@RequestParam("id") long id) throws DaoException {
		ModelMap model = new ModelMap();
		Product product;
		if (id == -1) {
			product = new Product();
			model.addAttribute("id", -1);
		} else {
			product = DaoFactory.getProductDao().getById(id);
			model.addAttribute("id", product.getId());
		}		
		model.addAttribute("product", product);
		return new ModelAndView("productForm", model);
	}
	
	@RequestMapping("/ProductSave.spr")
	public ModelAndView productSave(@RequestParam("id") long id, 
			@RequestParam("name") String name,			
			@RequestParam("description") String description) throws DaoException {

		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		if (id == -1) {
			DaoFactory.getProductDao().add(product);
			id = product.getId();
		} else {
			product.setId(id);
			DaoFactory.getProductDao().updateById(product);
		}
//		return productEdit(id);
		return listOfProducts();
	}
	
	@RequestMapping("/ProductListPage.spr")
	public ModelAndView listOfProducts() throws DaoException {
		List<Product> products = DaoFactory.getProductDao().getAllProduct();
		return new ModelAndView("productList", "listOfProducts", products);
	}
	
	@RequestMapping("/TradeOffers.spr")
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws DaoException {
		ModelMap model = new ModelMap();
		model.addAttribute("product", DaoFactory.getProductDao().getById(id));
		model.addAttribute("listOfLots", DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(id));
		model.addAttribute("listOfTenders", DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPriceDesc(id));
		return new ModelAndView("tradeOffers", model);
	}
}
