package com.bigroi.stock.controller.rendering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductRenderingController extends BaseRenderingController{

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/List.spr")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView activeProductList() {
		return createModelAndView("products");
	}

	@RequestMapping("/TradeOffers.spr")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView tradeOffers(@RequestParam("id") long id) {
		ModelAndView modelAndView = createModelAndView("tradeOffers");
		Product product = productService.getProductById(id);
		product.setName(labelService.getLabel(product.getName(), "name", getLanguage()));
		modelAndView.addObject("product", product);
		return modelAndView;
	}

	@RequestMapping("/admin/List.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView allProducts() {
		return createModelAndView("productsForAdmin");
	}

	@RequestMapping("/admin/Form.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView form(@RequestParam(value = "id", defaultValue = "-1") long id) {
		return createModelAndView("productForm");
	}

}
