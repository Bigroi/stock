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
	public ModelAndView goToWelcomePage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		String login;
		if (user != null) {
			login = user.getLogin();
		} else {
			login = "Anonymous";
		}
		return new ModelAndView("welcome", "login", login);
	}

	@RequestMapping("/Login.spr")
	public ModelAndView goToLoginPage(String message) {
		return new ModelAndView("login", "message", message);
	}

	@RequestMapping("/Registration.spr")
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
			return goToWelcomePage(session);
		} else {
			return goToLoginPage("Wrong password");
		}

	}

	private User getUser(String login, String password) {
		// TODO «¿√À”ÿ ¿
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
