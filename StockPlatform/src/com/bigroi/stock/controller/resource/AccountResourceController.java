package com.bigroi.stock.controller.resource;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account/json")
public class AccountResourceController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	public String accountPage(Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		Company company = ServiceFactory.getCompanyService().getCompanyById(user.getCompanyId());
		return new ResultBean(1, company).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	public String editAccount(
			@RequestParam("json") String json,
			Authentication loggedInUser) throws ServiceException {

		@SuppressWarnings("unchecked")
		Map<String, String> map = gson.fromJson(json, Map.class);
		
		
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		String password = map.get("password");
		if (password != null && !password.equals("")){
			user.setPassword(password);
		}
		Company company = ServiceFactory.getCompanyService().getCompanyById(user.getCompanyId());
		company.setPhone(map.get("phone"));
		company.setCountry(map.get("country"));
		company.setCity(map.get("city"));
		company.setAddress(map.get("address"));
		company.setLatitude(Double.parseDouble(map.get("latitude")));
		company.setLongitude(Double.parseDouble(map.get("longitude")));
		ServiceFactory.getUserService().updateCompanyAndUser(user, company);
		
		return new ResultBean(1, "account.edit.success").toString();
	}
}
