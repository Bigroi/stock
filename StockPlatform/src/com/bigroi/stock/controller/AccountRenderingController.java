package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class AccountRenderingController {
	
	private static final Logger logger = Logger.getLogger(AccountRenderingController.class);
	
	@RequestMapping("/AccounPageAuth.spr")
	public ModelAndView goToAccountPage(HttpSession session) throws ServiceException{
		logger.info("exection AccountRenderingController.goToAccountPage");
		logger.info(session);
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Company company = ServiceFactory.getUserService().getById(user.getCompanyId());
		model.addAttribute("company", company);	
		logger.info("exection AccountRenderingController.goToAccountPage successfully finished");
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
			@RequestParam("status") CompanyStatus status,
			HttpSession session) throws ServiceException {
		
		logger.info("exection AccountRenderingController.editAccount");
		logger.info(password);
		logger.info(name);
		logger.info(email);
		logger.info(phone);
		logger.info(regNumber);
		logger.info(country);
		logger.info(city);
		logger.info(status);
		logger.info(session);
		
		User user = (User) session.getAttribute("user");
		user.setPassword(password);
		session.setAttribute("user", user);
		Company company = new Company();
		company.setId(user.getCompanyId());
		company.setName(name);
		company.setEmail(email);
		company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		company.setStatus(status);
		ServiceFactory.getUserService().updateCompanyAndUser(user, company);
		
		logger.info("exection AccountRenderingController.editAccount successfully finished");
		return goToAccountPage(session);
	}

}
