package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;

@Controller
public class ProductRenderingComtroller {
	
	@RequestMapping("/ProductForm.spr")
	public ModelAndView productEdit(@RequestParam("id") long id) {
		Product product;
		if (id == -1) {
			product = new Product();
		} else {
//TODO «ј√Ћ”Ў ј получить продукт по id
			product = new Product();
			product.setName("ss");
			product.setDescription("asfffasdf");
		}
		return new ModelAndView("productForm", "product", product);
	}

}
