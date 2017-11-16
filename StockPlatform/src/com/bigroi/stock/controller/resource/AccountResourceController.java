package com.bigroi.stock.controller.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
	public @ResponseBody String accountPage(
			HttpSession session) 
					throws ServiceException {
		
		Map<String, Object> map = new HashMap<>();
		StockUser user = (StockUser) session.getAttribute("user");
		map.put("user", user);
		Company company = ServiceFactory.getCompanyService().getCompanyById(user.getCompanyId());
		map.put("company", company);
		return new ResultBean(1, map).toString();
	}

	@RequestMapping(value = "/Save.spr")
	public @ResponseBody String editAccount(
			@RequestParam("user") String newUserJson,
			@RequestParam("company") String newCompanyJson, 
			HttpSession session) 
					throws ServiceException {

		StockUser newUser = new Gson().fromJson(newUserJson, StockUser.class);
		Company newCompany = new Gson().fromJson(newCompanyJson, Company.class);
		StockUser oldUser = (StockUser) session.getAttribute("user");
		
		if (!newUser.equals(oldUser)){
			return new ResultBean(-1, "account.edit.names.error").toString();
		}
		
		ServiceFactory.getUserService().updateCompanyAndUser(newUser, newCompany);
		session.setAttribute("user", newUser);
		return new ResultBean(1, "account.edit.success").toString();
	}
}
