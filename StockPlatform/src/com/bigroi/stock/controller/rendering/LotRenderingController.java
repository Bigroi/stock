package com.bigroi.stock.controller.rendering;

import java.text.ParseException;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/lot")
public class LotRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			Authentication loggedInUser) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("lotForm");
		
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Lot lot = ServiceFactory.getLotService().getLot(id, user.getCompanyId());
		List<Product> productList = ServiceFactory.getProductService().getAllActiveProducts();
		
		modelAndView.addObject("lot", lot);
		modelAndView.addObject("listOfProducts", productList);
		return modelAndView;
	}
	
	@RequestMapping("/Save.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView save(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("minPrice") double minPrice,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volume") int volume,
			@RequestParam(value = "status", defaultValue = "DRAFT") Status status,
			Authentication loggedInUser) throws ParseException, ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		Lot lot = new Lot();
		lot.setId(id);
		lot.setDescription(description);
		lot.setProductId(productId);
		lot.setMinPrice(minPrice);
		lot.setSellerId(user.getId());
		lot.setDateStr(expDateStr);
		lot.setVolume(volume);
		lot.setStatus(status);		
		
		ServiceFactory.getLotService().merge(lot);
		
		return form(lot.getId(), loggedInUser);
	}
	
	@RequestMapping("/MyLots.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myLots");
	}
	
	@RequestMapping("/StartTrading.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView startTrading(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getLotService().startTrading(id);
		return myList();
	}
	
	@RequestMapping("/Cancel.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView lotCancel(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getLotService().cancel(id);
		return myList();
	}
}
