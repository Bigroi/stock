package com.bigroi.stock.controller.resource;

import javax.servlet.http.HttpServletRequest;

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
import com.bigroi.stock.security.AuthenticationHandler;
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
	public String accountPage(Authentication loggedInUser) throws ServiceException, CloneNotSupportedException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		user = user.cloneUser();
		user.setPassword("");
		user.setPasswordRepeat("");
		return new ResultBean(1, user, "").toString();
	}
	
	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String editAccount( @RequestParam("json") String json, Authentication loggedInUser) throws ServiceException {
		StockUser newUser = GsonUtil.getGson().fromJson(json, StockUser.class);
		StockUser loggedIn = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

		if (newUser.getPassword() != null && !newUser.getPassword().equals("")){
			if (!newUser.getPassword().equals(newUser.getPasswordRepeat())){
				return new ResultBean(-1, "label.account.error_password").toString();
			}
			loggedIn.setPassword(newUser.getPassword());
		}
		
		if (!newUser.getUsername().equals(loggedIn.getUsername()) 
				&& userService.getByUsername(newUser.getUsername()) != null) {
			return new ResultBean(-1, "label.account.error_login").toString();
		}
		
		loggedIn.getCompany().setPhone(newUser.getCompany().getPhone());
		loggedIn.setUsername(newUser.getUsername());
		userService.update(loggedIn);
		
		return new ResultBean(1, loggedIn, "label.account.edit_success").toString();
	}
	
	@RequestMapping(value = "/Registration.spr")
	@ResponseBody
	public String registration(HttpServletRequest request, @RequestParam("json") String json) throws ServiceException {
		
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		user.addAuthority(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
		
		if (userService.getByUsername(user.getUsername()) != null) {
			return new ResultBean(-1, "label.account.error_login").toString();
		}
		
		if (companyService.getByName(user.getCompany().getName()) != null) {
			return new ResultBean(-1, "label.account.error_name").toString();
		}
		
		if (companyService.getByRegNumber(user.getCompany().getRegNumber()) != null) {
			return new ResultBean(-1, "label.account.error_reg_number").toString();
		}
		
		if (!user.getPassword().equals(user.getPasswordRepeat())) {
			return new ResultBean(-1, "label.account.error_password").toString();
		}
		user.setUsername(user.getUsername().toLowerCase());
		user.getCompany().setType(AuthenticationHandler.getApplicationType(request.getContextPath()));
		
		userService.addUser(user);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResultBean(0, AuthenticationHandler.getMainPage(request.getContextPath()), null).toString();
	}
	
	@RequestMapping(value = "/ResetPassword.spr")
	@ResponseBody
	public String resetPassword(@RequestParam("json") String json) throws ServiceException {
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		userService.sendLinkResetPassword(user.getUsername());
		return new ResultBean(1, user, "label.account.password_reset").toString();
	}
}
