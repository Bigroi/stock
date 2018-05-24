package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/account")
public class AddressRenderingController extends BaseRenderingController {
	
	@RequestMapping("/ToAddress.spr")
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public ModelAndView toAddress(){ 
		return  createModelAndView("myAddresses"); 
	}
	
	@RequestMapping("/EditAddress.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView deal(@RequestParam("id") long id) throws  ServiceException {
		ModelAndView modelAndView = createModelAndView("addressForm");
		modelAndView.addObject("id", id);
		return modelAndView;
	}
}
