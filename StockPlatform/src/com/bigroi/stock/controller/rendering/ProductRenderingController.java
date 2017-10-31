package com.bigroi.stock.controller.rendering;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/product")
public class ProductRenderingController {

	@RequestMapping("/List.spr")
	public String activeProductList() throws ServiceException {
		return "productList";
	}

	@RequestMapping("/TradeOffers.spr")
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws ServiceException {
		Map<String, ?> map = ServiceFactory.getProductService().getTradeOffers(id);
		return new ModelAndView("tradeOffers", map);
	}

	@RequestMapping("/admin/List.spr")
	public ModelAndView allProducts(HttpSession session) throws ServiceException {
		List<Product> allProducts = ServiceFactory.getProductService().getAllProducts();
		return new ModelAndView("productListForAdmin", "listOfProducts", allProducts);
	}

	@RequestMapping("/admin/form.spr")
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id) 
					throws ServiceException {
		Product product = ServiceFactory.getProductService().getProductById(id);
		return new ModelAndView("productFormForAdmin", "product", product);
	}

	@RequestMapping("/admin/save.spr")
	public ModelAndView save(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			@RequestParam("name") String name,
			@RequestParam("description") String description, 
			HttpSession session)
			throws ServiceException {
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		ServiceFactory.getProductService().merge(product);
		return allProducts(session);
	}

	@RequestMapping("/admin/Delete.spr")
	public ModelAndView delete(@RequestParam("id") long id, HttpSession session)
			throws ServiceException {
		User user = (User) session.getAttribute("user");
		ServiceFactory.getProductService().delete(id, user.getCompanyId());
		return allProducts(session);
	}
}
