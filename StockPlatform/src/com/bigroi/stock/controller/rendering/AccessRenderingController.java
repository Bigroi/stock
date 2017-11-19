package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/access")
public class AccessRenderingController extends BaseRenderingController{
	
	//TODO move to js
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
			@RequestParam("city") String city
			) throws ServiceException{
		
		StockUser oldUser = ServiceFactory.getUserService().getByLogin(login);
		if (oldUser != null) {
			return new ModelAndView("registration","message", "registration.login.error" );
		}
		if (!password.equals(passwordRepeat)) {
			return new ModelAndView("registration","message", "registration.password.error" );		
		}
		Company company = new Company();
		company.setName(name);
		company.setEmail(email);
		company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		company.setStatus(CompanyStatus.NOT_VERIFIED);		
		StockUser user = new StockUser();
		user.setLogin(login);
		user.setPassword(password);
		user.setCompanyId(company.getId());
		ServiceFactory.getUserService().addUser(company, user);
		
		ModelAndView modelAndView = createModelAndView("registrationSuccess.jsp");
		modelAndView.addObject("user", user);
		return modelAndView;
	}
}
