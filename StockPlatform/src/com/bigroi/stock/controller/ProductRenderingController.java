package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;

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
//TODO ЗАГЛУШКА получить продукт по id
			product = new Product(); //ЗАГЛУШКА 
			product.setId(5);//ЗАГЛУШКА 
			product.setName("ss");//ЗАГЛУШКА 
			product.setDescription("asfffasdf");//ЗАГЛУШКА 
//end			
			model.addAttribute("id", product.getId());
		}		
		model.addAttribute("product", product);
		return new ModelAndView("productForm", model);
	}
	
	@RequestMapping("/ProductSave.spr")
	public ModelAndView productSave(@RequestParam("id") long id, 
			@RequestParam("name") String name,			
			@RequestParam("description") String description) {

		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		if ((id == 0) || (id == -1)) {
//TODO ЗАГЛУШКА Добавление продукта в базу и возврат id добавленного продукта
			id = 5; //Заглушка		
//end			
		} else {
//TODO Обновление (Update) продукта в базе
		}
		return productEdit(id);
	}

}
