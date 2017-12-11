package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;

@Controller
public class CommonRenderingController extends BaseRenderingController{

	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage() {
		return createModelAndView("mian");
	}

	@RequestMapping("/Login.spr")
	public ModelAndView goToLoginPage() {
		return createModelAndView("login");
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		ModelAndView modelAndView = createModelAndView("registration");
		modelAndView.addObject("company", new Company());
		return modelAndView;
	}
}
