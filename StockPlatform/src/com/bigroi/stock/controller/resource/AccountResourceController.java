package com.bigroi.stock.controller.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/account/json")
public class AccountResourceController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	public String accountPage(Authentication loggedInUser) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		map.put("user", user);
		Company company = ServiceFactory.getCompanyService().getCompanyById(user.getCompanyId());
		map.put("company", company);
		return new ResultBean(1, map).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	public String editAccount(
			@RequestParam("user") String newUserJson,
			@RequestParam("company") String newCompanyJson, 
			Authentication loggedInUser) throws ServiceException {

		StockUser newUser = new Gson().fromJson(newUserJson, StockUser.class);
		Company newCompany = new Gson().fromJson(newCompanyJson, Company.class);
		StockUser oldUser = (StockUser) loggedInUser.getPrincipal();
		
		if (!newUser.equals(oldUser)){
			return new ResultBean(-1, "account.edit.names.error").toString();
		}
		
		ServiceFactory.getUserService().updateCompanyAndUser(newUser, newCompany);
		return new ResultBean(1, "account.edit.success").toString();
	}
}
