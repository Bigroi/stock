package com.bigroi.stock.controller.rendering;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/access")
public class AccessRenderingController {
	
	
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
		
		User oldUser = ServiceFactory.getUserService().getByLogin(login);
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
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setCompanyId(company.getId());
		ServiceFactory.getUserService().addUser(company, user);
		session.setAttribute("user", user);
		return new ModelAndView("welcome", "user", user);
	}

	/*@RequestMapping("/Logout.spr")
	public ModelAndView logout(HttpSession session){
		if(session != null){
			session.invalidate();
		}
		return new ModelAndView("welcome","outMessage","you lefted account");
	}*/
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";//return new ModelAndView ()???
	}
	
	
	
}
