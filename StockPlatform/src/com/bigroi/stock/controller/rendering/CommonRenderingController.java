package com.bigroi.stock.controller.rendering;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CommonRenderingController {
	
/*
	@RequestMapping("/Index.spr")
	public String goToWelcomePage() {
		User user =  (User)SecurityContextHolder.getContext().getAuthentication();
			return "welcome";
	}*/
	

	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage() {
		ModelMap map = new ModelMap();
		Authentication loggedInUser =  SecurityContextHolder.getContext().getAuthentication();
		if (!(loggedInUser instanceof AnonymousAuthenticationToken)) {
			String username = loggedInUser.getName();
			Object objUser = loggedInUser.getPrincipal();
			map.addAttribute("userName", username);
			map.addAttribute("user", objUser);
		}
			return new ModelAndView("welcome", map);
	}

	@RequestMapping("/Login.spr")
	public String goToLoginPage() {
		return "login";
	}

	@RequestMapping("/Registration.spr")
	public String goToRegistrationPage() {
		return "registration";
	}
	
	@RequestMapping(value = "/admin/Index.spr")
	//@Secured("ADMIN")
	private String mainPage(Authentication loggedInUser) {
		loggedInUser =  SecurityContextHolder.getContext().getAuthentication();
		return (!(loggedInUser instanceof AnonymousAuthenticationToken)) ?  "adminMainPage" : "welcome";
	}
}
