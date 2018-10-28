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
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.util.StringUtils;

@Controller
@RequestMapping("/lot")
public class LotRenderingController extends BaseRenderingController{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private LotService lotService;
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value="id", defaultValue="-1") long id,
			Authentication loggedInUser){
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		ModelAndView modelAndView = createModelAndView("lotForm");
		List<Product> productList = productService.getAllActiveProducts();
		productList.forEach((product) -> product.setName(labelService.getLabel(product.getName(), "name", getLanguage())));
		
		List<CompanyAddress> addressList = addressService.getCompanyAddresses(user.getCompanyId());
		
		modelAndView.addObject("listOfProducts", productList);
		modelAndView.addObject("listOfAddresses", addressList);
		modelAndView.addObject("newLot", id < 1);
		modelAndView.addObject("id", id);
		modelAndView.addObject("foto", !StringUtils.isEmpty(lotService.getById(id, user.getCompanyId()).getFoto()));
		return modelAndView;
	}
	
	@RequestMapping("/TestForm.spr")
	public ModelAndView testForm(){
		ModelAndView modelAndView = createModelAndView("testLotForm");
		List<Product> productList = productService.getAllActiveProducts();
		productList.forEach((product) -> product.setName(labelService.getLabel(product.getName(), "name", getLanguage())));
		
		List<CompanyAddress> addressList = addressService.getCompanyAddresses(0);
		
		modelAndView.addObject("listOfProducts", productList);
		modelAndView.addObject("listOfAddresses", addressList);
		return modelAndView;
	}
	
	@RequestMapping("/MyLots.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() {
		return createModelAndView("myLots");
	}

}
