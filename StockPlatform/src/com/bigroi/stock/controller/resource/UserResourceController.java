package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/user/json/admin")
public class UserResourceController extends BaseResourseController{

	@RequestMapping(value = "/List.spr")
	public @ResponseBody String list() throws ServiceException {
		List<User> users = ServiceFactory.getUserService().getAllUsers();
		return new ResultBean(1, users).toString();
	}
	
	@RequestMapping(value = "/ResetPassword.spr")
	public @ResponseBody String ResetPassword(@RequestParam("login") String login) 
			throws ServiceException {
		ServiceFactory.getUserService().resetPassword(login);
		return new ResultBean(1, "user.password.reset.success").toString();
	}
}
