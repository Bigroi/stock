package com.bigroi.stock.controller.resource;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/access/json")
public class AccessResourceController extends BaseResourseController {

	@RequestMapping(value = "/Authenticate.spr")
	public @ResponseBody String authenticate(
			@RequestParam("json") String json, 
			HttpSession session) 
					throws ServiceException {
		StockUser bean = new Gson().fromJson(json, StockUser.class);
		StockUser user = ServiceFactory.getUserService().checkUserByPassword(bean.getLogin(), bean.getPassword());
		if (user != null) {
			session.setAttribute("user", user);
			return new ResultBean(1, "authenticate.success").toString();
		} else {
			return new ResultBean(-1, "authenticate.fail").toString();
		}

	}

	@RequestMapping(value = "/Registration.spr")
	public @ResponseBody String registration(
			@RequestParam("jsonUser") String jsonUser,
			@RequestParam("jsonCompany") String jsonCompany, 
			HttpSession session) throws ServiceException {
		StockUser user = new Gson().fromJson(jsonUser, StockUser.class);
		
		if (getUser(user.getLogin()) != null) {
			return new ResultBean(-1, "registration.login.error").toString();
		}
		
		Company company = new Gson().fromJson(jsonCompany, Company.class);
		company.setStatus(CompanyStatus.NOT_VERIFIED);
		ServiceFactory.getUserService().addUser(company, user);
		session.setAttribute("user", user);
		return new ResultBean(1, "registration.success").toString();
	}

	private StockUser getUser(String login) throws ServiceException {
		return ServiceFactory.getUserService().getByLogin(login);
	}

}
