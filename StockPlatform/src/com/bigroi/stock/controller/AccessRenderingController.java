package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class AccessRenderingController {
	
	private static final Logger logger = Logger.getLogger(AccessRenderingController.class);

	@RequestMapping("/Authenticate.spr")
	public ModelAndView authenticate(@RequestParam("login") String login, @RequestParam("password") String password,
			HttpSession session) throws ServiceException {
		logger.info("exection AccessRenderingController.authenticate");
		logger.info(login);
		logger.info(password);
		logger.info(session);
		User user = ServiceFactory.getUserService().getByLoginAndPassword(login, password);
		if (user != null) {
			session.setAttribute("user", user);
			logger.info("exection AccessRenderingController.authenticate - 'welcome', successfully finished");
			return new ModelAndView("welcome", "user", user);
		} else {
			logger.info("exection AccessRenderingController.authenticate - 'Wrong password', successfully finished");
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
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			HttpSession session) throws ServiceException{
		
		logger.info("exection AccessRenderingController.registration");
		logger.info(login);
		logger.info(password);
		logger.info(passwordRepeat);
		logger.info(name);
		logger.info(email);
		logger.info(phone);
		logger.info(regNumber);
		logger.info(country);
		logger.info(city);
		logger.info(session);
		
		if (getUser(login) != null) {
			logger.info("exection AccessRenderingController.registration - 'Login is used', successfully finished");
			return new ModelAndView("registration","message", "Login is used" );
		}
		if (!password.equals(passwordRepeat)) {
			logger.info("exection AccessRenderingController.registration - 'Passwords do not match', successfully finished");
			return new ModelAndView("registration","message", "Passwords do not match" );		
		}
		Company company = new Company();
		company.setName(name);
		company.setEmail(email);
		company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		company.setStatus(CompanyStatus.NOT_VERIFIED);		
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setCompanyId(company.getId());
		ServiceFactory.getUserService().addCompanyAndUser(company, user);
		session.setAttribute("user", user);
		logger.info("exection AccessRenderingController.registration - 'welcome', successfully finished");
		return new ModelAndView("welcome", "user", user);
	}

	private User getUser(String login) throws ServiceException {
		logger.info("exection AccessRenderingController.getUser");
		logger.info(login);
		logger.info("exection AccessRenderingController.getUser successfully finished");
		return ServiceFactory.getUserService().getByLogin(login);
	}
	
	
	
}
