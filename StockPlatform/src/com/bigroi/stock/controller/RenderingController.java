package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RenderingController {
	
	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage(){
		return new ModelAndView("welcome");		
	}
	
	@RequestMapping("/Login.spr")
	public ModelAndView goToLoginPage(){
		return new ModelAndView("login");		
	}
}
