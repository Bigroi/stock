package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonRenderingController extends BaseRenderingController{

	@RequestMapping("/Main.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView main() {
		return createModelAndView("main");
	}
	
	@RequestMapping("/Login.spr")
	public ModelAndView login() {
		return createModelAndView("login");
	}
	
	@RequestMapping("/Index.spr")
	public ModelAndView index() {
		return createModelAndView("index");
	}

}
