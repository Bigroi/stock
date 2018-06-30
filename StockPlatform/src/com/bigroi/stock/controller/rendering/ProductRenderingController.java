package com.bigroi.stock.controller.rendering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/product")
public class ProductRenderingController extends BaseRenderingController{

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/List.spr")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView activeProductList() throws ServiceException {
		return createModelAndView("products");
	}

	@RequestMapping("/TradeOffers.spr")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("tradeOffers");
		modelAndView.addObject("product", productService.getProductById(id));
		return modelAndView;
	}

	@RequestMapping("/admin/List.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView allProducts() throws ServiceException {
		return createModelAndView("productsForAdmin");
	}

	@RequestMapping("/admin/Form.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id) 
					throws ServiceException {
		return createModelAndView("productForm");
	}

}
