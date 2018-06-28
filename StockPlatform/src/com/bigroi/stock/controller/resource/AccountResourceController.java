package com.bigroi.stock.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;

@Controller
@RequestMapping("/account/json")
public class AccountResourceController extends BaseResourseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String accountPage(Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		user.setPassword("");
		return new ResultBean(1, user, "").toString();
	}
	
	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String editAccount(
			@RequestParam("json") String json,
			Authentication loggedInUser) throws ServiceException {

		StockUser newUser = GsonUtil.getGson().fromJson(json, StockUser.class);
		
		StockUser loggedIn = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		if (newUser.getPassword() != null && !newUser.getPassword().equals("")){
			loggedIn.setPassword(newUser.getPassword());
		}
		loggedIn.getCompany().setPhone(newUser.getCompany().getPhone());
		loggedIn.getCompany().getAddress().setAddress(newUser.getCompany().getAddress().getAddress());
		loggedIn.getCompany().getAddress().setCity(newUser.getCompany().getAddress().getCity());
		loggedIn.getCompany().getAddress().setCountry(newUser.getCompany().getAddress().getCountry());
		loggedIn.getCompany().getAddress().setLatitude(newUser.getCompany().getAddress().getLatitude());
		loggedIn.getCompany().getAddress().setLongitude(newUser.getCompany().getAddress().getLongitude());
		userService.update(loggedIn);
		
		loggedIn.setPassword("");
		return new ResultBean(1, loggedIn, "account.edit.success").toString();
	}
	
	@RequestMapping(value = "/Registration.spr")
	@ResponseBody
	public String registration(@RequestParam("json") String json) throws ServiceException {
		
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		user.addAuthority(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
		
		if (userService.getByUsername(user.getUsername()) != null) {
			return new ResultBean(-1, "label.registration.error_login").toString();
		}
		
		if (companyService.getByName(user.getCompany().getName()) != null) {
			return new ResultBean(-1, "label.registration.error_name").toString();
		}
		
		if (companyService.getByRegNumber(user.getCompany().getRegNumber()) != null) {
			return new ResultBean(-1, "label.registration.error_reg_number").toString();
		}
		
		if (!user.getPassword().equals(user.getPasswordRepeat())) {
			return new ResultBean(-1, "label.registration.error_password").toString();
		}
		user.setUsername(user.getUsername().toLowerCase());
		
		userService.addUser(user);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResultBean(0, "/product/List.spr", null).toString();
	}
	
	@RequestMapping(value = "/ResetPassword.spr")
	@ResponseBody
	public String ResetPassword(@RequestParam("json") String json) throws ServiceException {
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		userService.sendLinkResetPassword(user.getUsername());
		return new ResultBean(1, user, "label.account.password_reset").toString();
	}
}
