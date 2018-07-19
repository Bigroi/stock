package com.bigroi.stock.controller.rendering;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.security.AuthenticationHandler;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;

@Controller
public class CommonRenderingController extends BaseRenderingController{

	@Autowired
	private UserService userService;
	
	@RequestMapping("/Login.spr")
	public ModelAndView login() {
		return createModelAndView("login");
	}
	
	@RequestMapping("/Index.spr")
	public ModelAndView index(HttpServletRequest request, Authentication loggedInUser) {
		if (loggedInUser != null){ 
			Object user = loggedInUser.getPrincipal();
			if (user instanceof StockUser){
				return new ModelAndView("redirect:" + AuthenticationHandler.getMainPage(request.getContextPath()));
			}
		}
		return createModelAndView("index");
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		return createModelAndView("registration");
	}
	
	@RequestMapping(value = "/ResetForm.spr")
	public ModelAndView resetForm()
			throws ServiceException {
		return createModelAndView("resetForm");
	}

	@RequestMapping(value = "/ResetPassword.spr")
	public ModelAndView resetPassword(@RequestParam("code") String code, @RequestParam("email") String email)
			throws ServiceException {
		ModelAndView modelAndView = createModelAndView("resetPassw");
		String message;
		if (userService.changePassword(email, code)) {
			message = this.getLabelValue("label.resetPassw.success");
		} else {
			message = this.getLabelValue("label.resetPassw.error");
		}
		modelAndView
			.addObject("message", message)
			.addObject("user", null);
		return modelAndView;
	}
	
	@RequestMapping("/account/Form.spr")
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public ModelAndView form() {
		return createModelAndView("account");
	}
}
