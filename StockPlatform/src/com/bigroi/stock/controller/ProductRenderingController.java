package com.bigroi.stock.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(ProductRenderingController.class);

	@RequestMapping("/ProductForm.spr")
	public ModelAndView productEdit(@RequestParam("id") long id) throws DaoException {
		logger.info("exection ProductRenderingController.productEdit");
		logger.info(id);
		ModelMap model = new ModelMap();
		Product product;
		if (id == -1) {
			product = new Product();
			product.setId(-1);
			logger.info("execution ProductRenderingController.productEdit - create new product");
		} else {
			product = DaoFactory.getProductDao().getById(id);
			model.addAttribute("id", product.getId());
			logger.info("execution ProductRenderingController.productEdit - get edited product");
		}
		model.addAttribute("product", product);
		logger.info("exection ProductRenderingController.productEdit successfully finished");
		return new ModelAndView("productForm", model);
	}

	@RequestMapping("/ProductSave.spr")
	public ModelAndView productSave(@RequestParam("id") long id, @RequestParam("name") String name,
			@RequestParam("description") String description) throws DaoException {
		logger.info("exection ProductRenderingController.productSave");
		logger.info(id);
		logger.info(name);
		logger.info(description);
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		if (id == -1) {
			DaoFactory.getProductDao().add(product);
			id = product.getId();
			logger.info("execution ProductRenderingController.productSave - create new product");
		} else {
			product.setId(id);
			DaoFactory.getProductDao().updateById(product);
			logger.info("execution ProductRenderingController.productSave - update product");
		}
		// return productEdit(id);
		logger.info("exection ProductRenderingController.productSave successfully finished");
		return listOfProducts();
	}

	@RequestMapping("/ProductListPage.spr")
	public ModelAndView listOfProducts() throws DaoException {
		logger.info("exection ProductRenderingController.listOfProducts");
		List<Product> products = DaoFactory.getProductDao().getAllProduct();
		logger.info("exection ProductRenderingController.listOfProducts successfully finished");
		return new ModelAndView("productList", "listOfProducts", products);
	}

	@RequestMapping("/TradeOffers.spr")
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws DaoException {
		logger.info("exection ProductRenderingController.tradeOffers");
		logger.info(id);
		ModelMap model = new ModelMap();
		model.addAttribute("product", DaoFactory.getProductDao().getById(id));
		model.addAttribute("listOfLots", DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(id));
		model.addAttribute("listOfTenders", DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPriceDesc(id));
		logger.info("exection ProductRenderingController.tradeOffers successfully finished");
		return new ModelAndView("tradeOffers", model);
	}

	// -------------------------- controllers for admin's panel ------------------------------//

	@RequestMapping("/ProductListAdmin.spr")
	public ModelAndView listOfProductsForAdmin() throws DaoException {
		logger.info("exection ProductRenderingController.listOfProductsForAdmin");
		List<Product> allProducts = new ArrayList<>();
		List<Product> productsYes = DaoFactory.getProductDao().getArchiveYesProduct();
		List<Product> productsNo = DaoFactory.getProductDao().getArchiveNoProduct();
		allProducts.addAll(productsYes);
		allProducts.addAll(productsNo);
		logger.info("exection ProductRenderingController.listOfProductsForAdmin successfully finished");
		return new ModelAndView("productListForAdmin", "listOfProducts", allProducts);
	}

	@RequestMapping("/EditProduct.spr")
	public ModelAndView editProduct(@RequestParam("id") long id) throws DaoException {
		logger.info("exection ProductRenderingController.editProduct");
		logger.info(id);
		Product product;
		ModelMap model = new ModelMap();
		if (id != -1) {
			product = DaoFactory.getProductDao().getById(id);
			logger.info("exection ProductRenderingController.editProduct - get product");
		} else {
			product = new Product();
			logger.info("exection ProductRenderingController.editProduct - created new a product");
		}
		model.put("product", product);
		model.put("getId", id);
		logger.info("exection ProductRenderingController.editProduct successfully finished");
		return new ModelAndView("productFormForAdmin", model);
	}

	@RequestMapping("/ProdSave.spr")
	public ModelAndView prodSave(@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("description") String description) throws DaoException {
		logger.info("exection ProductRenderingController.prodSave");
		logger.info(id);
		logger.info(name);
		logger.info(description);
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		if (id != -1) {
			product.setId(id);
			DaoFactory.getProductDao().updateById(product);
			logger.info("exection ProductRenderingController.prodSave - update product");
		} else {
			product.getId();
			product.setArchive(true);
			DaoFactory.getProductDao().add(product);
			logger.info("exection ProductRenderingController.prodSave - create new a product");
		}
		logger.info("exection ProductRenderingController.prodSave successfully finished");
		//return editProduct(product.getId());
		return listOfProductsForAdmin();
	}

	@RequestMapping("/DeleteProduct.spr")
	public ModelAndView prodDelete(@RequestParam("id") long id) throws DaoException {
		logger.info("exection ProductRenderingController.prodDelete");
		logger.info(id);
		//DaoFactory.getProductDao().deletedById(id);
		DaoFactory.getProductDao().switchOnYes(id);
		logger.info("exection ProductRenderingController.prodDelete - delted product");
		logger.info("exection ProductRenderingController.prodDelete successfully finished");
		return listOfProductsForAdmin();

	}	
}
