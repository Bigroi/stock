package com.bigroi.stock.controller.rendering;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/lot")
public class LotRenderingController extends BaseRenderingController{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value="id", defaultValue="-1") long id,
			Authentication loggedInUser) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("lotForm");
		List<Product> productList = productService.getAllActiveProducts();
		List<CompanyAddress> addressList = addressService.getCompanyAddresses(((StockUser)loggedInUser.getPrincipal()).getCompanyId());
		
		modelAndView.addObject("listOfProducts", productList);
		modelAndView.addObject("listOfAddresses", addressList);
		modelAndView.addObject("newLot", id < 1);
		return modelAndView;
	}
	
	@RequestMapping("/MyLots.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myLots");
	}

}
