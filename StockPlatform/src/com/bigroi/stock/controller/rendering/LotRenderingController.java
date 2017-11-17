package com.bigroi.stock.controller.rendering;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/lot")
public class LotRenderingController {
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Lot lot = ServiceFactory.getLotService().getLot(id, user.getCompanyId());
		ModelAndView modelAndView = new ModelAndView("lotForm", "lot", lot);
		modelAndView.addObject("listOfProducts", 
				ServiceFactory.getProductService().getAllActiveProducts());
		return modelAndView;
	}
	
	@RequestMapping("/Save.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView save(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("minPrice") double minPrice,
			@RequestParam("sellerId") long salerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volumeOfLot") int volumeOfLot,
			@RequestParam("status") Status status,
			Authentication loggedInUser) throws ParseException, ServiceException {
		
		Lot lot = new Lot();
		lot.setId(id);
		lot.setDescription(description);
		lot.setProductId(productId);
		lot.setMinPrice(minPrice);
		lot.setSellerId(salerId);
		lot.setDateStr(expDateStr);
		lot.setVolume(volumeOfLot);
		lot.setStatus(status);		
		
		ServiceFactory.getLotService().merge(lot);
		
		return form(lot.getId(), loggedInUser);
	}
	
	@RequestMapping("/MyList.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList(Authentication loggedInUser) throws  ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();	
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(user.getCompanyId());
		return new ModelAndView("myLotList", "listOfLots", lots);
	}
	
	@RequestMapping("/StartTrading.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView startTrading(@RequestParam("id") long id, Authentication loggedInUser) throws ServiceException {
		ServiceFactory.getLotService().startTrading(id);
		return myList(loggedInUser);
	}
	
	@RequestMapping("/Cancel.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView lotCancel(@RequestParam("id") long id, Authentication loggedInUser) throws ServiceException {
		ServiceFactory.getLotService().cancel(id);
		return myList(loggedInUser);
	}
}
