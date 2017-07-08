package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class AccountRenderingController {
	
	@RequestMapping("/AccounPageAuth.spr")
	public ModelAndView goToAccountPage(HttpSession session) throws DaoException{
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Company company = DaoFactory.getCompanyDao().getById(user.getCompanyId());
		model.addAttribute("company", company);		
		return new ModelAndView("account", model);		
	}
	
	@RequestMapping("/AccountChangeAuth.spr")
	public ModelAndView editAccount(
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			HttpSession session) throws DaoException {
	
		//TODO Здесь будет одна транзакция
		User user = (User) session.getAttribute("user");
		user.setPassword(password);
		session.setAttribute("user", user);
		DaoFactory.getUserDao().update(user.getId(), user);
		
		Company company = new Company();
		company.setId(user.getCompanyId());
		company.setName(name);
		company.setEmail(email);
		company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		DaoFactory.getCompanyDao().updateById( company);;		
		
		return goToAccountPage(session);
	}

}
