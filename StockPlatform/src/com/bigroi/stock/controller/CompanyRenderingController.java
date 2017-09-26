package com.bigroi.stock.controller;

import java.util.ArrayList;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class CompanyRenderingController {

	@RequestMapping("/CompanyList.spr")
	public ModelAndView getListCompanyAll() throws DaoException {
		List<Company> list = new ArrayList<>();
		list = DaoFactory.getCompanyDao().getAllCompany();
		return new ModelAndView("companyList", "listOfCompany", list);
	}

	@RequestMapping("/ChangeStatus.spr")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws DaoException {
		Company  company = DaoFactory.getCompanyDao().getById(id);
		switch (company.getStatus()) {
		case NOT_VERIFIED :
			DaoFactory.getCompanyDao().setStatusVerified(company);
			break;
		case VERIFIED :
			DaoFactory.getCompanyDao().setStatusRevoked(company);
			break;
		case REVOKED :
			DaoFactory.getCompanyDao().setStatusNotVerified(company);
			break;
		}
		return getListCompanyAll();
	}

	@RequestMapping("CancelApll.spr")
	public ModelAndView cancelApplication(@RequestParam("id") long id) throws DaoException {
		Company company = DaoFactory.getCompanyDao().getById(id);
		if (company.getStatus() == CompanyStatus.REVOKED) {
			DaoFactory.getLotDao().setStatusCancel(company.getId());
			DaoFactory.getTenderDao().setStatusCancel(company.getId());
		}
		return getListCompanyAll();

	}
}
