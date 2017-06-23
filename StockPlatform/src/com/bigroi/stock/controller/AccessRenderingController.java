package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class AccessRenderingController {

	@RequestMapping("/Authenticate.spr")
	public ModelAndView authenticate(@RequestParam("login") String login, @RequestParam("password") String password,
			HttpSession session) throws DaoException {
		User user = DaoFactory.getUserDao().getByLoginAndPassword(login, password);
		if (user != null) {
			session.setAttribute("user", user);
			return new ModelAndView("welcome", "user", user);
		} else {
			return new ModelAndView("login", "message", "Wrong password");
		}
	}

	@RequestMapping("/Registation.spr")
	public ModelAndView registration(
			@RequestParam("login") String login, 
			@RequestParam("password") String password,
			@RequestParam("passwordRepeat") String passwordRepeat, 
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") int phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			HttpSession session) throws DaoException {
				
		if (getUser(login) != null) {
			return new ModelAndView("registration","message", "Login is used" );
		}
		if (!password.equals(passwordRepeat)) {
			return new ModelAndView("registration","message", "Passwords do not match" );		
		}
		//TODO ����� ����� ���� ����������
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		DaoFactory.getUserDao().add(user);
		Company company = new Company();
		company.setUserId(user.getId());
		company.setName(name);
		company.setEmail(email);
		//TODO company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		DaoFactory.getCompanyDao().add(company);
		session.setAttribute("user", user);
		return new ModelAndView("welcome", "user", user);
	}

	private User getUser(String login) throws DaoException {
		return DaoFactory.getUserDao().getByLogin(login);
	}
	
	
	
}
