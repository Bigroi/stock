package com.bigroi.stock.controller.rendering;

import java.text.ParseException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/tender")
public class TenderRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			Authentication loggedInUser) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("tenderForm");
		
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Tender tender = ServiceFactory.getTenderService().getTender(id, user.getCompanyId());
		List<Product> products = ServiceFactory.getProductService().getAllActiveProducts();
		
		modelAndView.addObject("tender", tender);
		modelAndView.addObject("listOfProducts", products);
		return modelAndView;
	}

	@RequestMapping("/Save.spr")
	public ModelAndView save(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("maxPrice") double maxPrice,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volume") int volume,
			@RequestParam(value = "status", defaultValue = "DRAFT") Status status,
			Authentication loggedInUser) throws ParseException, ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		Tender tender = new Tender();
		tender.setId(id);
		tender.setDescription(description);
		tender.setProductId(productId);
		tender.setMaxPrice(maxPrice);
		tender.setCustomerId(user.getCompanyId());
		tender.setDateStr(expDateStr);
		tender.setVolume(volume);
		tender.setStatus(status);
		
		ServiceFactory.getTenderService().merge(tender);

		return myList();
	}
	
	@RequestMapping("/MyTenders.spr")
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myTenders");
	}
	
	@RequestMapping("/StartTrading.spr")
	public ModelAndView startTrading(@RequestParam("id") long id) throws  ServiceException {
		ServiceFactory.getTenderService().startTrading(id);
		return myList();
	}
	
	@RequestMapping("/Cancel.spr")
	public ModelAndView cancel(@RequestParam("id") long id) throws  ServiceException {
		ServiceFactory.getTenderService().cancel(id);
		return myList();
	}
}
