package com.bigroi.stock.controller.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.UserRoles;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.UserRole;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/access/json")
public class AccessResourceController extends BaseResourseController {

	@RequestMapping(value = "/Registration.spr")
	@ResponseBody
	public String registration(@RequestParam("jsonUser") String jsonUser,
			@RequestParam("jsonCompany") String jsonCompany, 
			@RequestParam("jsonUserRole") String jsonUserRole) throws ServiceException {
		
		StockUser user = new Gson().fromJson(jsonUser, StockUser.class);
		
		if (getUser(user.getLogin()) != null) {
			return new ResultBean(-1, "registration.login.error").toString();
		}
		
		Company company = new Gson().fromJson(jsonCompany, Company.class);
		company.setStatus(CompanyStatus.NOT_VERIFIED);
		//-------------------------------------------------
		UserRoles userRole = new Gson().fromJson(jsonUserRole, UserRoles.class) ;// check plz this code area
		userRole.setRole(UserRole.ROLE_USER.toString());
		
		ServiceFactory.getUserService().addUser(company, user, userRole);
		//-------------------------------------------------
		return new ResultBean(1, "registration.success").toString();
	}

	private StockUser getUser(String login) throws ServiceException {
		return ServiceFactory.getUserService().getByLogin(login);
	}

}
