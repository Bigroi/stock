package com.bigroi.stock.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class ProductRenderingController {

	private static final Logger logger = Logger.getLogger(ProductRenderingController.class);

	@RequestMapping("/ProductForm.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView productEdit(@RequestParam("id") long id) throws ServiceException {
		logger.info("exection ProductRenderingController.productEdit");
		logger.info(id);
		ModelMap model = ServiceFactory.getProductService().callEditProduct(id);
		logger.info("exection ProductRenderingController.productEdit successfully finished");
		return new ModelAndView("productForm", model);
	}

	@RequestMapping("/ProductSave.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView productSave(@RequestParam("id") long id, @RequestParam("name") String name,
			@RequestParam("description") String description) throws ServiceException {
		logger.info("exection ProductRenderingController.productSave");
		logger.info(id);
		logger.info(name);
		logger.info(description);
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		ServiceFactory.getProductService().callSaveProduct(id, product);
		logger.info("exection ProductRenderingController.productSave successfully finished");
		return listOfProducts();
	}

	@RequestMapping("/ProductListPage.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView listOfProducts() throws ServiceException {
		logger.info("exection ProductRenderingController.listOfProducts");
		List<Product> products = ServiceFactory.getProductService().getAllProduct();
		logger.info("exection ProductRenderingController.listOfProducts successfully finished");
		return new ModelAndView("productList", "listOfProducts", products);
	}

	@RequestMapping("/TradeOffers.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws ServiceException {
		logger.info("exection ProductRenderingController.tradeOffers");
		logger.info(id);
		ModelMap model = ServiceFactory.getProductService().tradeOffers(id);
		logger.info("exection ProductRenderingController.tradeOffers successfully finished");
		return new ModelAndView("tradeOffers", model);
	}

	// -------------------------- controllers for admin's panel ------------------------------//

	@RequestMapping("/ProductListAdmin.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView listOfProductsForAdmin(HttpSession session) throws ServiceException {
		logger.info("exection ProductRenderingController.listOfProductsForAdmin");
		List<Product> allProducts = ServiceFactory.getProductService().getListOfProductsForAdmin(session);
		logger.info("exection ProductRenderingController.listOfProductsForAdmin successfully finished");
		return new ModelAndView("productListForAdmin", "listOfProducts", allProducts);
	}

	@RequestMapping("/EditProduct.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView editProduct(@RequestParam("id") long id) throws ServiceException {
		logger.info("exection ProductRenderingController.editProduct");
		logger.info(id);
		ModelMap model = ServiceFactory.getProductService().callEditProductForAdmin(id);
		logger.info("exection ProductRenderingController.editProduct successfully finished");
		return new ModelAndView("productFormForAdmin", model);
	}

	@RequestMapping("/ProdSave.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView prodSave(@RequestParam("id") long id, @RequestParam("name") String name,
			@RequestParam("description") String description, HttpSession session)
			throws ServiceException {
		logger.info("exection ProductRenderingController.prodSave");
		logger.info(id);
		logger.info(name);
		logger.info(description);
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		ServiceFactory.getProductService().callSaveProductForAdmin(id, product);
		logger.info("exection ProductRenderingController.prodSave successfully finished");
		return listOfProductsForAdmin(session);
	}

	@RequestMapping("/DeleteProduct.spr")
	//@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView prodDelete(@RequestParam("id") long id, HttpSession session)
			throws ServiceException {
		logger.info("exection ProductRenderingController.prodDelete");
		logger.info(id);
		ServiceFactory.getProductService().callDeleteProduct(id, session);
		logger.info("exection ProductRenderingController.prodDelete - delted product");
		logger.info("exection ProductRenderingController.prodDelete successfully finished");
		return listOfProductsForAdmin(session);

	}
}
