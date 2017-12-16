package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/account")
public class AccountRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	@Secured("ROLE_USER")
	public ModelAndView form() throws ServiceException{
		ModelAndView modelAndView = createModelAndView("account");
		return modelAndView;		
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		ModelAndView modelAndView = createModelAndView("registration");
		modelAndView.addObject("company", new Company());
		return modelAndView;
	}
}
