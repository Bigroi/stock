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
//TODO �������� �������� ������� �� id
			product = new Product(); //�������� 
			product.setId(5);//�������� 
			product.setName("ss");//�������� 
			product.setDescription("asfffasdf");//�������� 
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
//TODO �������� ���������� �������� � ���� � ������� id ������������ ��������
			id = 5; //��������		
//end			
		} else {
//TODO ���������� (Update) �������� � ����
		}
		return productEdit(id);
	}

}
