package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Role;
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
			@RequestParam("city") String city,
			@RequestParam("latitude") float latitude,
			@RequestParam("longitude") float longitude
			) throws ServiceException{
		
		StockUser oldUser = ServiceFactory.getUserService().getByLogin(login);
		Company company = new Company();
		company.setName(name);
		company.setEmail(email);
		company.setPhone(phone);
		company.setRegNumber(regNumber);
		company.setCountry(country);
		company.setCity(city);
		company.setStatus(CompanyStatus.NOT_VERIFIED);
		company.setLongitude(longitude);
		company.setLatitude(latitude);
		
		StockUser user = new StockUser();
		user.setLogin(login);
		user.setPassword(password);
		
		if (oldUser != null) {
			ModelAndView modelAndView = createModelAndView("registration");
			modelAndView.addObject("message", getLableValue("lable.registration.loggin_error"));
			modelAndView.addObject("user", user);
			modelAndView.addObject("company", company);
			return modelAndView;
		}
		if (!password.equals(passwordRepeat)) {
			ModelAndView modelAndView = createModelAndView("registration");
			modelAndView.addObject("message", getLableValue("lable.registration.password_error"));
			modelAndView.addObject("user", user);
			modelAndView.addObject("company", company);
			return modelAndView;
		}
		
		
		ServiceFactory.getUserService().addUser(company, user, new Role[]{Role.ROLE_USER});
		
		ModelAndView modelAndView = createModelAndView("registrationSuccess");
		return modelAndView;
	}
}
