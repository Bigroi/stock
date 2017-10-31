package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CommonRenderingController {
	
	@RequestMapping("/Index.spr")
	public String goToWelcomePage() {
		return "welcome";
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
	private String mainPage() {
		return "adminMainPage";
	}
}
