package com.bigroi.stock.controller.rendering;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/tender")
public class TenderRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			Authentication loggedInUser
			) throws ServiceException {
		ModelAndView modelAndView = createModelAndView("tenderForm");
		List<Product> products = ServiceFactory.getProductService().getAllActiveProducts();
		List<Address> addressList = ServiceFactory.getAddressService().getCompanyAddresses(((StockUser)loggedInUser.getPrincipal()).getCompanyId());
		
		modelAndView.addObject("newTender", id < 0);
		modelAndView.addObject("listOfProducts", products);
		modelAndView.addObject("listOfAddresses", addressList);
		return modelAndView;
	}
	
	@RequestMapping("/MyTenders.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myTenders");
	}
}
