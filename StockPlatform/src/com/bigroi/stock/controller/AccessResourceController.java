package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
public class AccessResourceController extends ResourseBeanException {
	
	private static final Logger logger = Logger.getLogger(AccessResourceController.class);
	
	@RequestMapping(value = "/Authenticate.spr")
	@ResponseBody
	public String authenticate(@RequestParam("json") String json, HttpSession session) throws DaoException {
		logger.info("exection AccessResourceController.authenticate");
		logger.info(json);
		logger.info(session);
	
			User bean = new Gson().fromJson(json, User.class);
			User user = DaoFactory.getUserDao().getByLoginAndPassword(bean.getLogin(), bean.getPassword());
			if (user != null) {
				session.setAttribute("user", user);
				logger.info("exection AccessResourceController.authenticate - 'authenticate.success', successfully finished");
				return new ResultBean(1, "authenticate.success").toString();
			} else {
				logger.info("exection AccessResourceController.authenticate - 'authenticate.fail', successfully finished");
				return new ResultBean(1, "authenticate.fail").toString();
			}
		
	}

	@RequestMapping(value = "/Registration.spr")
	@ResponseBody
	public String registration(
			@RequestParam("json") String json, HttpSession session) throws DaoException {//TODO: падает при запуске контроллера
		
		logger.info("exection AccessResourceController.registration");
		logger.info(json);
		logger.info(session);
		User userAuth = new Gson().fromJson(json, User.class);
		
			if (getUser(userAuth.getLogin()) != null) {
				logger.info("exection AccessResourceController.registration - 'Login is used', successfully finished");
				return new ResultBean(1, "Login is used").toString();
			}
			
			String password = userAuth.getPassword();
			String passwordRepeat = userAuth.getPassword();
			if (!password .equals(passwordRepeat)) {
				logger.info("exection AccessResourceController.registration - 'Passwords do not match', successfully finished");
				return new ResultBean(1, "Passwords do not match").toString();
			}
			Company company = new Gson().fromJson(json, Company.class);
			DaoFactory.getCompanyDao().add(company);
			User user = new Gson().fromJson(json, User.class);
			DaoFactory.getUserDao().add(user);
			session.setAttribute("user", user);
			logger.info("exection AccessResourceController.registration successfully finished");
			return new ResultBean(1, user).toString();
		
	}
	
	private User getUser(String login) throws DaoException {
		logger.info("exection AccessResourceController.getUser");
		logger.info(login);
		logger.info("exection AccessResourceController.getUser successfully finished");
		return DaoFactory.getUserDao().getByLogin(login);
	}

}
