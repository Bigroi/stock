package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/user/json/admin")
public class UserResourceController extends BaseResourseController{

	@RequestMapping(value = "/List.spr")
	@ResponseBody
	public String list() throws ServiceException, TableException {
		List<StockUser> users = ServiceFactory.getUserService().getAllUsers();
		Table<StockUser> table = new Table<>(StockUser.class, users);
		return new ResultBean(1, table).toString();
	}
	
	@RequestMapping(value = "/ResetPassword.spr")
	@ResponseBody
	public String ResetPassword(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getUserService().resetPassword(id);
		return new ResultBean(1, "user.password.reset.success").toString();
	}
}
