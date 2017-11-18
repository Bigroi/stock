package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/user/admin")
public class UserRenderingController extends BaseRenderingController{

	@RequestMapping(value = "/ChangeUserPass.spr")
	public ModelAndView listOfUser() throws ServiceException {
		return createModelAndView("changeUserPass");
	}

	@RequestMapping(value = "/ResetPassword.spr")
	public ModelAndView changePass(@RequestParam("login") String login) throws ServiceException {
		ServiceFactory.getUserService().resetPassword(login);
		return listOfUser();
	}
}
