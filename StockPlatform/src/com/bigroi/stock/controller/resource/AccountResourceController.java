package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.AddressForUI;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account/json")
public class AccountResourceController extends BaseResourseController {

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
		ServiceFactory.getUserService().update(loggedIn);
		
		loggedIn.setPassword("");
		return new ResultBean(1, loggedIn, "account.edit.success").toString();
	}
	
	@RequestMapping(value = "/Registration.spr")
	@ResponseBody
	public String registration(@RequestParam("json") String json) 
			throws ServiceException {
		
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		user.addAuthority(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
		
		if (ServiceFactory.getUserService().getByUsername(user.getUsername()) != null) {
			return new ResultBean(-1, "registration.login.error").toString();
		}
		
		if (!user.getPassword().equals(user.getPasswordRepeat())) {
			return new ResultBean(-1, "registration.password.error").toString();
		}
		user.setUsername(user.getUsername().toLowerCase());
		
		ServiceFactory.getUserService().addUser(user);
		return new ResultBean(1, user, "registration.success").toString();
	}
	
	@RequestMapping(value = "/ResetPassword.spr")
	@ResponseBody
	public String ResetPassword(@RequestParam("json") String json) throws ServiceException {
		StockUser user = GsonUtil.getGson().fromJson(json, StockUser.class);
		ServiceFactory.getUserService().sendLinkResetPassword(user.getUsername());
		return new ResultBean(1, user, "user.password.reset.success").toString();
	}
	
	@RequestMapping(value = "/AddressesList.spr")
	@ResponseBody
	public String getAddresses() throws ServiceException, TableException{
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Address> listAddresses = ServiceFactory.getAddressService().getCompanyAddresses(user.getCompanyId());
		List<AddressForUI> addressesForUI = listAddresses.stream().map(AddressForUI::new).collect(Collectors.toList());
		TableResponse<AddressForUI> table = new TableResponse<>(AddressForUI.class, addressesForUI);
		return new ResultBean(1, table, null).toString();
		
	}
}
