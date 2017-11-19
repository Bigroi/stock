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

	@RequestMapping(value = "/Users.spr")
	public ModelAndView listOfUser() throws ServiceException {
		return createModelAndView("users");
	}

	//TODO move to js
	@RequestMapping(value = "/ResetPassword.spr")
	public ModelAndView changePass(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getUserService().resetPassword(id);
		return listOfUser();
	}
}
