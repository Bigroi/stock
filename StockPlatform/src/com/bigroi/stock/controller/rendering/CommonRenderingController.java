package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.StockUser;


@Controller
public class CommonRenderingController extends BaseRenderingController{

	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage() {
		return createModelAndView("welcome");
	}

	@RequestMapping("/Login.spr")
	public ModelAndView goToLoginPage() {
		return createModelAndView("login");
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		return createModelAndView("registration");
	}
	
	@RequestMapping(value = "/admin/Index.spr")
	@Secured(value = {"ADMIN"})
	private  ModelAndView mainPage() {
		return createModelAndView("adminMainPage");
	}
}
