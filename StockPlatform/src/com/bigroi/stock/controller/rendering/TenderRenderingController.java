package com.bigroi.stock.controller.rendering;

import java.text.ParseException;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	//TODO move to js
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			Authentication loggedInUser) throws ServiceException {
		checkTender(id);
		ModelAndView modelAndView = createModelAndView("tenderForm");
		
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Tender tender = ServiceFactory.getTenderService().getTender(id, user.getCompanyId());
		List<Product> products = ServiceFactory.getProductService().getAllActiveProducts();
		
		modelAndView.addObject("tender", tender);
		modelAndView.addObject("listOfProducts", products);
		return modelAndView;
	}

	//TODO move to js
	@RequestMapping("/Save.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView save(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam(value = "productId", defaultValue = "-1") long productId,
			@RequestParam(value = "maxPrice", defaultValue = "0") double maxPrice,
			@RequestParam(value = "expDate", defaultValue = "") String expDateStr,
			@RequestParam("volume") int volume,
			@RequestParam("maxVolume") int maxVol,
			Authentication loggedInUser) throws ParseException, ServiceException {
		checkTender(id);
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		Tender tender = ServiceFactory.getTenderService().getTender(id, user.getCompanyId());
		if (tender.getId() < 0){
			tender.setProductId(productId);
			tender.setCustomerId(user.getCompanyId());
			tender.setDateStr(expDateStr);
			tender.setStatus(Status.DRAFT);
		}
		tender.setDescription(description);
		tender.setVolume(volume);
		tender.setMaxPrice(maxPrice);
		tender.setMaxVolume(maxVol);

		ServiceFactory.getTenderService().merge(tender);

		return myList();
	}
	
	@RequestMapping("/MyTenders.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myTenders");
	}
	
	//TODO move to js
	@RequestMapping("/StartTrading.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView startTrading(@RequestParam("id") long id) throws  ServiceException {
		checkTender(id);
		ServiceFactory.getTenderService().startTrading(id);
		return myList();
	}
	
	//TODO move to js
	@RequestMapping("/Cancel.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView cancel(@RequestParam("id") long id) throws  ServiceException {
		checkTender(id);
		ServiceFactory.getTenderService().cancel(id);
		return myList();
	}
	
	private void checkTender(long id) throws ServiceException{
		if (id < 0){
			return;
		}
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Tender tender = ServiceFactory.getTenderService().getTender(id, 0);
		if (tender.getCustomerId() != user.getCompanyId()){
			throw new SecurityException("User have no permission to modify Lot with id = " + id);
		}
	}
}
