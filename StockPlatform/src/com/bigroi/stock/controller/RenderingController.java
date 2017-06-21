package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;

@Controller
public class RenderingController {

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

	@RequestMapping("/Authenticate.spr")
	public ModelAndView Authenticate(@RequestParam("login") String login, @RequestParam("password") String password,
			HttpSession session) {
		User user;
		user = getUser(login, password);
		if (user != null) {
			session.setAttribute("user", user);
			return goToWelcomePage();
		} else {
			return goToLoginPage("Wrong password");
		}

	}

	@RequestMapping("/Registation.spr")
	public ModelAndView registration(@RequestParam("login") String login, 
			@RequestParam("password") String password,
			@RequestParam("passwordRepeat") String passwordRepeat, 
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city) {
				
		if (getUser(login) != null) {
			return new ModelAndView("registration","message", "Login is used" );
		}
		if (!password.equals(passwordRepeat)) {
			return new ModelAndView("registration","message", "Passwords do not match" );		
		}
		//TODO Сохранение компании(и юзера) в базу
		return new ModelAndView("welcome");
	}

	private User getUser(String login) {
		// TODO ЗАГЛУШКА
		if ("Admin".equals(login)) {
			User user = new User();
			user.setLogin("Admin");
			user.setPassword("1");
			return user;
		} else {
			return null;
		}

	}

	private User getUser(String login, String password) {
		// TODO ЗАГЛУШКА
		if ("Admin".equals(login) && "1".equals(password)) {
			User user = new User();
			user.setLogin(login);
			user.setPassword(password);
			return user;
		} else {
			return null;
		}
	}

}
