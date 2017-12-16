package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/product")
public class ProductRenderingController extends BaseRenderingController{

	@RequestMapping("/List.spr")
	public ModelAndView activeProductList() throws ServiceException {
		return createModelAndView("products");
	}

	@RequestMapping("/TradeOffers.spr")
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("tradeOffers");
		modelAndView.addObject("product", ServiceFactory.getProductService().getProductById(id));
		return modelAndView;
	}

	@RequestMapping("/admin/List.spr")
	public ModelAndView allProducts() throws ServiceException {
		return createModelAndView("productsForAdmin");
	}

	@RequestMapping("/admin/Form.spr")
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id) 
					throws ServiceException {
		return createModelAndView("productForm");
	}

}
