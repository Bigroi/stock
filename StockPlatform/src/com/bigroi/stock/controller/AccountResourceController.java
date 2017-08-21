package com.bigroi.stock.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class AccountResourceController extends ResourseBeanException {

	private static final Logger logger = Logger.getLogger(AccountResourceController.class);
	
	@RequestMapping(value = "/AccounPageAuth.spr")
	@ResponseBody
	public String goToAccountPage(@RequestParam("json") String json, HttpSession session) throws DaoException {
		logger.info("execution AccountResourceController.goToAccountPage");
		logger.info(session);
		    User bean = new Gson().fromJson(json, User.class);
			Map<String,Object> map = new HashMap<>();
			User user = (User) session.getAttribute("user");
			map.put("user", user);
			Company company = DaoFactory.getCompanyDao().getById(bean.getCompanyId());
			map.put("company", company);
			logger.info("execution AccountResourceController.goToAccountPage successfully finished");
			return new ResultBean(1, map).toString();
		
	}

  @RequestMapping(value = "AccountChangeAuth.spr")
	@ResponseBody
	public String editAccount(@RequestParam("json") String json, HttpSession session) throws DaoException {
	  
	  	logger.info("exection AccountResourceController.editAccount");
		logger.info(json);
		logger.info(session);
		
		    User userBean = new Gson().fromJson(json, User.class);
			User user = (User) session.getAttribute("user");
			user.setPassword(userBean.getPassword());
			session.setAttribute("user", user);
			DaoFactory.getUserDao().updateById(user);

			Company companyBean = new Gson().fromJson(json, Company.class);
			DaoFactory.getCompanyDao().updateById(companyBean);
			Object obj = goToAccountPage(json, session);

			logger.info("exection AccountResourceController.editAccount successfully finished");
			return new ResultBean(1, obj).toString();

	}
}
