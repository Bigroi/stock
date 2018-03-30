package com.bigroi.stock.controller.resource;

import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account/json")
public class AccountResourceController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String accountPage(Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		Company company = ServiceFactory.getCompanyService().getCompanyById(user.getCompanyId());
		return new ResultBean(1, company, "").toString();
	}
	
	@RequestMapping(value = "/AddUser.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String veryfiUser(@RequestParam("json") String json) throws ServiceException {
		InviteUser inviteUser = GsonUtil.getGson().fromJson(json, InviteUser.class);
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	    ServiceFactory.getUserService().addInviteUser(inviteUser, user.getCompanyId());
		return new ResultBean(1, "account.invite.success").toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String editAccount(
			@RequestParam("json") String json,
			Authentication loggedInUser) throws ServiceException {

		@SuppressWarnings("unchecked")
		Map<String, String> map = GsonUtil.getGson().fromJson(json, Map.class);
		
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
		
		return new ResultBean(1, map, "account.edit.success").toString();
	}
	
	@RequestMapping(value = "/Registration.spr")
	@ResponseBody
	public String registration(@RequestParam("json") String json) 
			throws ServiceException {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = GsonUtil.getGson().fromJson(json, Map.class);
		
		StockUser user = new StockUser();
		user.setUsername(map.get("username"));
		user.setPassword(map.get("password"));
		String passwordRepeat = map.get("passwordRepeat");
		
		if (ServiceFactory.getUserService().getByUsername(user.getUsername()) != null) {
			return new ResultBean(-1, "registration.login.error").toString();
		}
		
		if (!user.getPassword().equals(passwordRepeat)) {
			return new ResultBean(-1, "registration.password.error").toString();
		}
		
		Company company = new Company();
		company.setName(map.get("name"));
		company.setRegNumber(map.get("regNumber"));
		company.setStatus(CompanyStatus.NOT_VERIFIED);
		company.setPhone(map.get("phone"));
		company.setCountry(map.get("country"));
		company.setCity(map.get("city"));
		company.setAddress(map.get("address"));
		company.setLatitude(Double.parseDouble(map.get("latitude")));
		company.setLongitude(Double.parseDouble(map.get("longitude")));
		
		ServiceFactory.getUserService().addUser(company, user, new Role[]{Role.ROLE_USER});
		return new ResultBean(1, map, "registration.success").toString();
	}
	
	@RequestMapping(value = "/ResetPassword.spr")
	@ResponseBody
	public String ResetPassword(@RequestParam("json") String json) throws ServiceException {
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		ServiceFactory.getUserService().sendLinkResetPassword(user.getUsername());
		return new ResultBean(1, user, "user.password.reset.success").toString();
	}
}
