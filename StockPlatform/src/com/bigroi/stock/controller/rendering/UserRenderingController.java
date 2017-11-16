package com.bigroi.stock.controller.rendering;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/user/admin")
public class UserRenderingController {

	@RequestMapping(value = "/ChangeUserPass.spr")
	public ModelAndView listOfUser() throws ServiceException {
		List<StockUser> user = ServiceFactory.getUserService().getAllUsers();
		return new ModelAndView("changeUserPass", "listOfUser", user);
	}

	@RequestMapping(value = "/ResetPassword.spr")
	public ModelAndView changePass(@RequestParam("login") String login) throws ServiceException {
		ServiceFactory.getUserService().resetPassword(login);
		return listOfUser();
	}
}
