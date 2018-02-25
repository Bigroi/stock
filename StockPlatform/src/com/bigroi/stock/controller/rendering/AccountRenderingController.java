package com.bigroi.stock.controller.rendering;

import java.net.MalformedURLException;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account")
public class AccountRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form() throws ServiceException{
		return createModelAndView("account");		
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		return createModelAndView("registration");
	}
	
	@RequestMapping("/AddUser.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView goToInviteUserPage() {
		return createModelAndView("addUser");
	}
	
	@RequestMapping(value = "/Join.spr", method = RequestMethod.GET)
	public ModelAndView addInviteUser(@RequestParam("code") String code) throws ServiceException, MalformedURLException { 
		ModelAndView modelAndView = createModelAndView("checkPassw");
		InviteUser inviteUser = new InviteUser();
		StockUser user = new StockUser();
		inviteUser = ServiceFactory.getUserService().getInviteUserCode(code);///
		//user move to service
		user.setUsername(inviteUser.getInviteEmail());
		user.setPassword(inviteUser.getGeneratedKey());
		user.setCompanyId(inviteUser.getCompanyId());////
		ServiceFactory.getUserService().addUserByInvite(user, new Role[] { Role.ROLE_USER });
		modelAndView.addObject("userName", user.getUsername());
		modelAndView.addObject("userPassw", user.getPassword());
		return modelAndView;
	}
}
