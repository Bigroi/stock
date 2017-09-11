package com.bigroi.stock.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class CompanyResourceController {

	@RequestMapping("/CompanyList.spr")
	public ModelAndView getListCompanyAll(HttpSession session) throws DaoException {
		session.getAttribute("user");
		List<Company> list = DaoFactory.getCompanyDao().getAllCompany();
		return new ModelAndView("companyList", "listOfCompany", list);
	}

	@RequestMapping("/ChangeStatus.spr")
	public ModelAndView changeStatus(@RequestParam("id") long id, HttpSession session) throws DaoException {
		session.getAttribute("user");
		ModelMap model = new ModelMap();
		Company company;
		if (id > 0) {
			company = DaoFactory.getCompanyDao().getById(id);
			model.put("id", company);
			model.put("listOfStatus", DaoFactory.getCompanyDao().getAllCompany());
		}
		return new ModelAndView("companyForm", model);

	}
}
