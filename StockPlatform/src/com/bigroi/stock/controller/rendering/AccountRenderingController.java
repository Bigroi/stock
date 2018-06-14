package com.bigroi.stock.controller.rendering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountRenderingController extends BaseRenderingController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/Form.spr")
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public ModelAndView form() throws ServiceException {
		return createModelAndView("account");
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		return createModelAndView("registration");
	}

	@RequestMapping(value = "/ResetPassword.spr", method = RequestMethod.GET)
	public ModelAndView resetPassword(@RequestParam("code") String code, @RequestParam("email") String email)
			throws ServiceException {
		ModelAndView modelAndView = createModelAndView("resetPassw");
		if (userService.checkCodeAndEmail(email, code)) {
			userService.changePassword(email);
			String message = this.getLabelValue("label.resetPassw.success");
			modelAndView.addObject("message", message);
		} else {
			String message = this.getLabelValue("label.resetPassw.error");
			modelAndView.addObject("message", message);
		}
		return modelAndView;
	}
}
