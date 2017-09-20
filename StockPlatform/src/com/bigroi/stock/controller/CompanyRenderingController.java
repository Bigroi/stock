package com.bigroi.stock.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class CompanyRenderingController {

	@RequestMapping("/CompanyList.spr")
	public ModelAndView getListCompanyAll(HttpSession session) throws DaoException {
		User user = (User) session.getAttribute("user");
		List<Company> list = new ArrayList<>();
		if (user != null) {
			list = DaoFactory.getCompanyDao().getAllCompany();
		} else {
			return new ModelAndView("login");
		}
		return new ModelAndView("companyList", "listOfCompany", list);
	}

	@RequestMapping("/ChangeStatus.spr")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws DaoException {
		// session.getAttribute("user");
		ModelMap model = new ModelMap();
		Company company = new Company();
		
		;
		if (id > -1) {
			company = DaoFactory.getCompanyDao().getById(id);
		}
		model.put("id", company);
		model.put("listOfStatus", id);
		model.put("FullListOfStatus", DaoFactory.getCompanyDao().getAllCompany());
		return new ModelAndView("companyForm", model);
	}

	@RequestMapping("/SaveStatus.spr")
	public ModelAndView saveStatus(@RequestParam("id") long id, Company company) throws DaoException {
		if (id > -1) {
			company.setId(id);
			DaoFactory.getCompanyDao().updateStatus(company, id);
		}

		return changeStatus(company.getId());
	}

	@RequestMapping("CancelApll.spr")
	public ModelAndView cancelApplication(@RequestParam("id") long id, HttpSession session) throws DaoException {
		Company company = DaoFactory.getCompanyDao().getById(id);
		if (company.getStatus() == CompanyStatus.REVOKED) {
			Lot lot = DaoFactory.getLotDao().getById(id);
			DaoFactory.getLotDao().setStatusCancel(lot);
			Tender tender = DaoFactory.getTenderDao().getById(id);
			DaoFactory.getTenderDao().setStatusCancel(tender);
		}
		return getListCompanyAll(session);

	}

}
