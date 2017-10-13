package com.bigroi.stock.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class ProductRenderingController {

	private static final Logger logger = Logger.getLogger(ProductRenderingController.class);

	@RequestMapping("/ProductForm.spr")
	public ModelAndView productEdit(@RequestParam("id") long id) throws DaoException, ServiceException {
		logger.info("exection ProductRenderingController.productEdit");
		logger.info(id);
		
		ModelMap model = new ModelMap();
		Product product;
		if (id == -1) {
			product = new Product();
			product.setId(-1);
			logger.info("execution ProductRenderingController.productEdit - create new product");
		} else {
			//product = DaoFactory.getProductDao().getById(id);
			product = ServiceFactory.getProductService().getById(id);
			model.addAttribute("id", product.getId());
			logger.info("execution ProductRenderingController.productEdit - get edited product");
		}
		model.addAttribute("product", product);
		
		logger.info("exection ProductRenderingController.productEdit successfully finished");
		return new ModelAndView("productForm", model);
	}

	@RequestMapping("/ProductSave.spr")
	public ModelAndView productSave(@RequestParam("id") long id, @RequestParam("name") String name,
			@RequestParam("description") String description) throws DaoException, ServiceException {
		logger.info("exection ProductRenderingController.productSave");
		logger.info(id);
		logger.info(name);
		logger.info(description);
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		if (id == -1) {
			//DaoFactory.getProductDao().add(product);
			ServiceFactory.getProductService().addProduct(product);
			id = product.getId();
			logger.info("execution ProductRenderingController.productSave - create new product");
		} else {
			product.setId(id);
			//DaoFactory.getProductDao().updateById(product);
			ServiceFactory.getProductService().updateProduct(product);
			logger.info("execution ProductRenderingController.productSave - update product");
		}
		// return productEdit(id);
		logger.info("exection ProductRenderingController.productSave successfully finished");
		return listOfProducts();
	}

	@RequestMapping("/ProductListPage.spr")
	public ModelAndView listOfProducts() throws DaoException, ServiceException {
		logger.info("exection ProductRenderingController.listOfProducts");
		//List<Product> products = DaoFactory.getProductDao().getAllProduct();
		List<Product> products = ServiceFactory.getProductService().getAllProduct();
		logger.info("exection ProductRenderingController.listOfProducts successfully finished");
		return new ModelAndView("productList", "listOfProducts", products);
	}

	@RequestMapping("/TradeOffers.spr")
	public ModelAndView tradeOffers(@RequestParam("id") long id) throws DaoException, ServiceException {
		logger.info("exection ProductRenderingController.tradeOffers");
		logger.info(id);
		ModelMap model = ServiceFactory.getProductService().tradeOffers(id);
		
		/*ModelMap model = new ModelMap();
		model.addAttribute("product", DaoFactory.getProductDao().getById(id));
		model.addAttribute("listOfLots", DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(id));
		model.addAttribute("listOfTenders", DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPriceDesc(id));*/
		logger.info("exection ProductRenderingController.tradeOffers successfully finished");
		return new ModelAndView("tradeOffers", model);
	}

	// -------------------------- controllers for admin's panel ------------------------------//

	@RequestMapping("/ProductListAdmin.spr")
	public ModelAndView listOfProductsForAdmin(HttpSession session) throws DaoException {
		logger.info("exection ProductRenderingController.listOfProductsForAdmin");
		User user = (User) session.getAttribute("user");
		List<Product> allProducts = new ArrayList<>();
		List<Product> productsYes = new ArrayList<>();
		List<Product> productsNo = new ArrayList<>();
		if(user != null && user.getLogin().equals("Admin")){
			 productsYes = DaoFactory.getProductDao().getArchiveYesProduct();
			 productsNo = DaoFactory.getProductDao().getArchiveNoProduct();
		}else if(user == null || !(user.getLogin().equals("Admin"))){
			productsNo = DaoFactory.getProductDao().getArchiveNoProduct();
		}
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
			@RequestParam("description") String description,
			HttpSession session) throws DaoException {
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
			product.setArchive(false);
			DaoFactory.getProductDao().add(product);
			logger.info("exection ProductRenderingController.prodSave - create new a product");
		}
		logger.info("exection ProductRenderingController.prodSave successfully finished");
		return listOfProductsForAdmin(session);
	}

	@RequestMapping("/DeleteProduct.spr")
	public ModelAndView prodDelete(@RequestParam("id") long id, HttpSession session) throws DaoException, MailManagerException {
		logger.info("exection ProductRenderingController.prodDelete");
		logger.info(id);
		User user = (User) session.getAttribute("user");
		if(user != null){
		DaoFactory.getProductDao().switchOnYes(id);
		DaoFactory.getLotDao().LotStatusCancelByProductId(id);
		DaoFactory.getTenderDao().TenderStatusCancelByProductId(id);
		Company company =  DaoFactory.getCompanyDao().getById(user.getCompanyId());
		MessagerFactory.getMailManager().send(company.getEmail(), "deleteComapny", " some text");
		}
		logger.info("exection ProductRenderingController.prodDelete - delted product");
		logger.info("exection ProductRenderingController.prodDelete successfully finished");
		return listOfProductsForAdmin(session);

	}	
}
