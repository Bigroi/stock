package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account")
public class AccountRenderingController extends BaseRenderingController {

	@RequestMapping("/Form.spr")
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public ModelAndView form() throws ServiceException {
		return createModelAndView("account");
	}

	@RequestMapping("/Registration.spr")
	public ModelAndView goToRegistrationPage() {
		return createModelAndView("registration");
	}

	@RequestMapping("/AddUser.spr")
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public ModelAndView goToInviteUserPage() {
		return createModelAndView("addUser");
	}

	@RequestMapping(value = "/ResetPassword.spr", method = RequestMethod.GET)
	public ModelAndView resetPassword(@RequestParam("code") String code, @RequestParam("email") String email)
			throws ServiceException {
		ModelAndView modelAndView = createModelAndView("resetPassw");
		if (ServiceFactory.getUserService().checkCodeAndEmail(email, code)) {
			ServiceFactory.getUserService().changePassword(email);
			String message = this.getLabelValue("label.resetPassw.success");
			modelAndView.addObject("message", message);
		} else {
			String message = this.getLabelValue("label.resetPassw.error");
			modelAndView.addObject("message", message);
		}
		return modelAndView;
	}
	
	@RequestMapping("/ToAddress.spr")
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public ModelAndView toAddress(){
		return new ModelAndView("address");
	}
}
