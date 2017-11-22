package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.UserRole;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Role;
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
			@RequestParam("jsonCompany") String jsonCompany) throws ServiceException {
		
		StockUser user = new Gson().fromJson(jsonUser, StockUser.class);
		
		if (getUser(user.getLogin()) != null) {
			return new ResultBean(-1, "registration.login.error").toString();
		}
		
		Company company = new Gson().fromJson(jsonCompany, Company.class);
		company.setStatus(CompanyStatus.NOT_VERIFIED);
		
		UserRole userRole = new UserRole();
		List<UserRole> listRole = new ArrayList<>();
		userRole.setRole(Role.ROLE_USER);
		listRole.add(userRole);
		//XXX the same as in rendering controller
		ServiceFactory.getUserService().addUser(company, user, listRole);
		return new ResultBean(1, "registration.success").toString();
	}

	private StockUser getUser(String login) throws ServiceException {
		return ServiceFactory.getUserService().getByLogin(login);
	}

}
