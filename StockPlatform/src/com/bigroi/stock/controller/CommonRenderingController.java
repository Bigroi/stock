package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonRenderingController {
	
	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage() {
		return new ModelAndView("welcome");
	}

	@RequestMapping("/LoginPage.spr")
	public ModelAndView goToLoginPage(String message) {
		return new ModelAndView("login", "message", message);
	}

	@RequestMapping("/RegistrationPage.spr")
	public ModelAndView goToRegistrationPage() {
		return new ModelAndView("registration");
	}
}
