package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;

@Controller
public class AccountRenderingController {
	
	@RequestMapping("/AccounPageAuth.spr")
	public ModelAndView goToAccountPage(HttpSession session){
		User user = (User) session.getAttribute("user");		
		return new ModelAndView("account", "user", user);
		
	}

}
