package com.bigroi.stock.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPageRenderingController {

	private static final Logger logger = Logger.getLogger(AdminPageRenderingController.class);
	
	@RequestMapping(value = "/AdminMainPage.spr")
	private ModelAndView mainPage() {
		String message = "admin rulez";
		logger.info("AdminPageRenderingController is running");
		return new ModelAndView("adminMainPage", "message", message);
	}
}
