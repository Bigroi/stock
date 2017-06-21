package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RenderingController {
	
	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcome(){
		return new ModelAndView("welcome");
		
	}
}
