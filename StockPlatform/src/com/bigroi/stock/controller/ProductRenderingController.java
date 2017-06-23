package com.bigroi.stock.controller;

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
	public ModelAndView productEdit(@RequestParam("id") long id) {
		ModelMap model = new ModelMap();
		Product product;
		if (id == -1) {
			product = new Product();
			model.addAttribute("id", -1);
		} else {
//TODO «¿√À”ÿ ¿ ÔÓÎÛ˜ËÚ¸ ÔÓ‰ÛÍÚ ÔÓ id
			product = new Product(); //«¿√À”ÿ ¿ 
			product.setId(5);//«¿√À”ÿ ¿ 
			product.setName("ss");//«¿√À”ÿ ¿ 
			product.setDescription("asfffasdf");//«¿√À”ÿ ¿ 
//end			
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
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		if (id == -1) {
	DaoFactory.getProductDao().add(product);
			id = product.getId();
		} else {
			DaoFactory.getProductDao().update(product.getId(), product);
		}
		return productEdit(id);
	}

}
