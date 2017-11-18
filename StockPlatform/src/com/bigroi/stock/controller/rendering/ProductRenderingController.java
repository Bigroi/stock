package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/product")
public class ProductRenderingController extends BaseRenderingController{

	@RequestMapping("/List.spr")
	public ModelAndView activeProductList() throws ServiceException {
		return createModelAndView("productList");
	}

	@RequestMapping("/TradeOffers.spr")
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("tradeOffers");
		modelAndView.addObject("product", ServiceFactory.getProductService().getProductById(id));
		return modelAndView;
	}

	@RequestMapping("/admin/List.spr")
	public ModelAndView allProducts() throws ServiceException {
		return createModelAndView("productListForAdmin");
	}

	@RequestMapping("/admin/Form.spr")
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id) 
					throws ServiceException {
		ModelAndView modelAndView = createModelAndView("productForm");
		Product product = ServiceFactory.getProductService().getProductById(id);
		modelAndView.addObject("product", product);
		return modelAndView;
	}

	@RequestMapping("/admin/Save.spr")
	public ModelAndView save(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			@RequestParam("name") String name,
			@RequestParam("description") String description)
			throws ServiceException {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		ServiceFactory.getProductService().merge(product);
		return allProducts();
	}

	@RequestMapping("/admin/Delete.spr")
	public ModelAndView delete(@RequestParam("id") long id)
			throws ServiceException {
		//FIXME productId is not necessary
		ServiceFactory.getProductService().delete(id, 0);
		return allProducts();
	}
}
