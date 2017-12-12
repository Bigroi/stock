package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account")
public class AccountRenderingController extends BaseRenderingController{
	
	//TODO move to js
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
	
	//TODO move to js
	@RequestMapping("/Save.spr")
	public ModelAndView save(
			@RequestParam("password") String password,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude
			) throws ServiceException {
		
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		if (password != null && !password.equals("")){
			user.setPassword(password);
		}
		Company company = ServiceFactory.getCompanyService().getCompanyById(user.getCompanyId());
		company.setId(user.getCompanyId());
		company.setEmail(email);
		company.setPhone(phone);
		company.setCountry(country);
		company.setCity(city);
		company.setLatitude(latitude);
		company.setLongitude(longitude);
		ServiceFactory.getUserService().updateCompanyAndUser(user, company);
		
		return form();
	}

}
