package com.bigroi.stock.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CommonRenderingController {
	
	private static final Logger logger = Logger.getLogger(CommonRenderingController.class);
	
	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage() {
		logger.info("exection CommonRenderingController.goToWelcomePage");
		logger.info("exection CommonRenderingController.goToWelcomePage successfully finished");
		return new ModelAndView("welcome");
	}

	@RequestMapping("/LoginPage.spr")
	public ModelAndView goToLoginPage(String message) {
		logger.info("exection CommonRenderingController.goToLoginPage");
		logger.info(message);
		logger.info("exection CommonRenderingController.goToLoginPage successfully finished");
		return new ModelAndView("login", "message", message);
	}

	@RequestMapping("/RegistrationPage.spr")
	public ModelAndView goToRegistrationPage() {
		logger.info("exection CommonRenderingController.goToRegistrationPage");
		logger.info("exection CommonRenderingController.goToRegistrationPage successfully finished");
		return new ModelAndView("registration");
	}
	
}
