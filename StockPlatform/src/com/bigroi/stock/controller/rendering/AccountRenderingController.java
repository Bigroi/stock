package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AccountRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	@Secured("ROLE_USER")
	public ModelAndView form() throws ServiceException{
		ModelAndView modelAndView = createModelAndView("account");
		
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		modelAndView.addObject("user", user);
		
		Company company = ServiceFactory.getUserService().getById(user.getCompanyId());
		modelAndView.addObject("company", company);
		
		return modelAndView;		
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
			@RequestParam("status") CompanyStatus status
			) throws ServiceException {
		
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		user.setPassword(password);
		
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
		
		return form();
	}

}
