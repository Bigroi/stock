package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class AccountResourceController {

	private static final Logger logger = Logger.getLogger(AccountResourceController.class);
	
	@RequestMapping(value = "/AccounPageAuthJSON.spr")
	@ResponseBody
	public String goToAccountPage(HttpSession session) {
		logger.info("execution AccountResourceController.goToAccountPage");
		logger.info(session);
		try {
			ModelMap model = new ModelMap();
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			Company company = DaoFactory.getCompanyDao().getById(user.getCompanyId());
			model.addAttribute("company", company);
			logger.info("execution AccountResourceController.goToAccountPage successfully finished");
			return new ResultBean(1, model).toString();
		} catch (DaoException e) {
			logger.info("execution AccountResourceController.goToAccountPage - catch DaoException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}

  @RequestMapping(value = "AccountChangeAuthJSON.spr")//TODO: на форму не приходит company, только user
	@ResponseBody
	public String editAccount(@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city, 
			@RequestParam("status") CompanyStatus status, 
			@RequestParam("json") String json, HttpSession session) {
	  
	  	logger.info("exection AccountResourceController.editAccount");
	  	logger.info(password);
		logger.info(name);
		logger.info(email);
		logger.info(phone);
		logger.info(regNumber);
		logger.info(country);
		logger.info(city);
		logger.info(status);
		logger.info(json);
		logger.info(session);
		
		try {
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
			DaoFactory.getCompanyDao().updateById(company);
			Object obj = goToAccountPage(session);

			logger.info("exection AccountResourceController.editAccount successfully finished");
			return new ResultBean(1, obj).toString();

		} catch (DaoException e) {
			logger.info("execution AccountResourceController.editAccount - catch DaoException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
}
