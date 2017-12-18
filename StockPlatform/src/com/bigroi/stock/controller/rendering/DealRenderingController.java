package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/deal")
public class DealRenderingController extends BaseRenderingController {

	@RequestMapping("/MyDeals.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView myList() throws  ServiceException {
		return createModelAndView("myDeals");
	}
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView deal(@RequestParam("id") long id) throws  ServiceException {
		ModelAndView modelAndView = createModelAndView("deal");
		modelAndView.addObject("id", id);
		return modelAndView;
	}

}
