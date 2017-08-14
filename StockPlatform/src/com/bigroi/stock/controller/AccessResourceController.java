package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.apache.catalina.mbeans.UserMBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class AccessResourceController {
	
	
	@RequestMapping(value = "/Authenticate.spr")
	@ResponseBody
	public String authenticate(@RequestParam("json") String json, HttpSession session) {
		try {
			User bean = new Gson().fromJson(json, User.class);
			User user = DaoFactory.getUserDao().getByLoginAndPassword(bean.getLogin(), bean.getPassword());
			if (user != null) {
				session.setAttribute("user", user);
				return new ResultBean(1, "authenticate.success").toString();
			} else {
				return new ResultBean(1, "authenticate.fail").toString();
			}
		} catch (DaoException e) {
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}

	@RequestMapping(value = "/RegisrationJSON.spr")
	@ResponseBody
	public String registration(@RequestParam("login") String login,
			@RequestParam("password") String password,
			@RequestParam("passwordRepeat") String passwordRepeat, 
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone,
			@RequestParam("regNumber") String regNumber, 
			@RequestParam("country") String country,
			@RequestParam("city") String city, 
			@RequestParam("json") String json, HttpSession session) {
		try {
			if (getUser(login) != null) {
				return new ResultBean(1, "Login is used").toString();
			}
			if (!password.equals(passwordRepeat)) {
				return new ResultBean(1, "Passwords do not match").toString();
			}
			Company company = new Company();
			company.setName(name);
			company.setEmail(email);
			company.setPhone(phone);
			company.setRegNumber(regNumber);
			company.setCountry(country);
			company.setCity(city);
			company.setStatus(CompanyStatus.NOT_VERIFIED);
			DaoFactory.getCompanyDao().add(company);
			User user = new User();
			user.setLogin(login);
			user.setPassword(password);
			user.setCompanyId(company.getId());
			DaoFactory.getUserDao().add(user);
			session.setAttribute("user", user);
			return new ResultBean(1, user).toString();
		} catch (DaoException e) {
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
	private User getUser(String login) throws DaoException {
		return DaoFactory.getUserDao().getByLogin(login);
	}

}
