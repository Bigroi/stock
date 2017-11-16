package com.bigroi.stock.controller.rendering;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
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
@RequestMapping("/account")
public class AccountRenderingController {
	
	@RequestMapping("/Form.spr")
	@Secured("USER")
	public ModelAndView form(HttpSession session) throws ServiceException{
		Map<String, Object> map = new HashMap<>();
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		map.put("user", user);
		Company company = ServiceFactory.getUserService().getById(user.getCompanyId());
		map.put("company", company);	
		return new ModelAndView("account", map);		
	}
	
	@RequestMapping("/Save.spr")
	public ModelAndView save(
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("status") CompanyStatus status,
			HttpSession session) throws ServiceException {
		
		StockUser user = (StockUser) session.getAttribute("user");
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
		
		return form(session);
	}

}
