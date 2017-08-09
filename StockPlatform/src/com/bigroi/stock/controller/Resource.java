package com.bigroi.stock.controller;



import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.google.gson.Gson;

@Controller
public class Resource {

	private Gson gson = new Gson();

	//-------------------AccessRenderingController------------------------------
	
	@RequestMapping(value = "/AuthenticateJSON.spr")
	@ResponseBody
	public String authenticate(@RequestParam("login") String login, @RequestParam("password") String password,
			HttpSession session) throws DaoException {
		User user = DaoFactory.getUserDao().getByLoginAndPassword(login, password);
		if (user != null) {
			session.setAttribute("user", user);
			return gson.toJson(user);
		} else {
			String message = "Wrong passwrod";
			return gson.toJson(message);
		}
	}

	@RequestMapping(value = "/RegisrationJSON.spr")
	@ResponseBody
	public String registration(@RequestParam("login") String login, @RequestParam("password") String password,
			@RequestParam("passwordRepeat") String passwordRepeat, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, @RequestParam("country") String country,
			@RequestParam("city") String city, HttpSession session) throws DaoException {

		if (getUser(login) != null) {
			String message = "Login is used";
			return gson.toJson(message);
		}
		if (!password.equals(passwordRepeat)) {
			String message = "Passwords do not match";
			return gson.toJson(message);
		}
				Company company = new Company();
				company.setName(name);
				company.setEmail(email);
				company.setPhone(phone);
				company.setRegNumber(regNumber);
				company.setCountry(country);
				company.setCity(city);
				company.setStatus(CompanyStatus.NOT_VERIFIED);
				DaoFactory.getCompanyDao().add(company);		
				User user = new User();
				user.setLogin(login);
				user.setPassword(password);
				user.setCompanyId(company.getId());
				DaoFactory.getUserDao().add(user);		
				session.setAttribute("user", user);
				return gson.toJson(user);
	}
	private User getUser(String login) throws DaoException {
		return DaoFactory.getUserDao().getByLogin(login);
	}
	
	//-------------------AccountRenderingController------------------------------
	
	@RequestMapping("/AccounPageAuthJSON.spr")
	@ResponseBody
	public String goToAccountPage(HttpSession session) throws DaoException{
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Company company = DaoFactory.getCompanyDao().getById(user.getCompanyId());
		model.addAttribute("company", company);		
		return gson.toJson(model);
		
	}
	
	@RequestMapping("AccountChangeAuthJSON.spr")
	@ResponseBody
	public String editAccount(@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("status") CompanyStatus status,
			HttpSession session) throws DaoException{
		
		User user = (User) session.getAttribute("user");
		user.setPassword(password);
		session.setAttribute("user", user);
		DaoFactory.getUserDao().updateById(user);
		
		Company company = new Company();
		company.setId(user.getCompanyId());
		company.setName(name);
		company.setEmail(email);
		company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		company.setStatus(status);
		
		DaoFactory.getCompanyDao().updateById( company);	
		
		return gson.toJson(goToAccountPage(session));
		
		
	}
	
	

}
