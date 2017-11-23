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

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/lot")
public class LotRenderingController extends BaseRenderingController{
	
	//TODO move to js
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			Authentication loggedInUser) throws ServiceException {
		checkLot(id);
		ModelAndView modelAndView = createModelAndView("lotForm");
		
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Lot lot = ServiceFactory.getLotService().getLot(id, user.getCompanyId());
		List<Product> productList = ServiceFactory.getProductService().getAllActiveProducts();
		
		modelAndView.addObject("lot", lot);
		modelAndView.addObject("listOfProducts", productList);
		return modelAndView;
	}
	
	private void checkLot(long id) throws ServiceException{
		if (id < 0){
			return;
		}
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Lot lot = ServiceFactory.getLotService().getLot(id, 0);
		if (lot.getSellerId() != user.getCompanyId()){
			throw new SecurityException("User have no permission to modify Lot with id = " + id);
		}
	}

	//TODO move to js
	@RequestMapping("/Save.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView save(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam(value = "productId", defaultValue = "-1") long productId,
			@RequestParam(value = "minPrice", defaultValue = "0") double minPrice,
			@RequestParam(value = "expDate", defaultValue = "") String expDateStr,
			@RequestParam("volume") int volume,
			@RequestParam("minVolume") int minVol,
			Authentication loggedInUser) throws ParseException, ServiceException {
		checkLot(id);
		
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		Lot lot = ServiceFactory.getLotService().getLot(id, user.getCompanyId());
		if (lot.getId() < 0) {
			lot.setProductId(productId);
			lot.setSellerId(user.getCompanyId());
			lot.setDateStr(expDateStr);
			lot.setStatus(Status.DRAFT);
			lot.setMinPrice(minPrice);
		}
		lot.setVolume(volume);
		lot.setMinVolume(minVol);
		lot.setDescription(description);

		ServiceFactory.getLotService().merge(lot);

		return myList();
	}
	
	@RequestMapping("/MyLots.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myLots");
	}
	
	//TODO move to js
	@RequestMapping("/StartTrading.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView startTrading(@RequestParam("id") long id) throws ServiceException {
		checkLot(id);
		ServiceFactory.getLotService().startTrading(id);
		return myList();
	}
	
	//TODO move to js
	@RequestMapping("/Cancel.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView lotCancel(@RequestParam("id") long id) throws ServiceException {
		checkLot(id);
		ServiceFactory.getLotService().cancel(id);
		return myList();
	}
}
