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
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.StockUser;


@Controller
public class CommonRenderingController {

	@RequestMapping("/Index.spr")
	public ModelAndView goToWelcomePage() {
	ModelAndView modelAndView = new ModelAndView("welcome");
		Authentication loggedInUser =  SecurityContextHolder.getContext().getAuthentication();
		if (loggedInUser instanceof StockUser) {
			Object objUser = loggedInUser.getPrincipal();
			modelAndView.addObject("user", objUser);
		}
			return modelAndView;
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
	@Secured(value = {"ADMIN"})
	private  ModelAndView mainPage(Authentication loggedInUser) {
		ModelAndView modelAndView = new ModelAndView("adminMainPage");
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		modelAndView.addObject(user);
		//return (!(loggedInUser instanceof AnonymousAuthenticationToken)) ?  "adminMainPage" : "welcome";
		return modelAndView;
	}
}
