package com.bigroi.stock.controller;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;
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
	
	private static final Logger logger = Logger.getLogger(CompanyRenderingController.class);

	@RequestMapping("/CompanyList.spr")
	public ModelAndView getListCompanyAll() throws DaoException {
		logger.info("exection CompanyRenderingController.getListCompanyAll");
		List<Company> list = new ArrayList<>();
		list = DaoFactory.getCompanyDao().getAllCompany();
		logger.info("exection CompanyRenderingController.getListCompanyAll successfully finished");
		return new ModelAndView("companyList", "listOfCompany", list);
	}

	@RequestMapping("/ChangeStatus.spr")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws DaoException {
		logger.info("exection CompanyRenderingController.changeStatus");
		logger.info(id);
		Company  company = DaoFactory.getCompanyDao().getById(id);
		switch (company.getStatus()) {
		case NOT_VERIFIED :
			DaoFactory.getCompanyDao().setStatusVerified(company);
			logger.info("exection CompanyRenderingController.changeStatus - chahge status VERIFIED");
			break;
		case VERIFIED :
			DaoFactory.getCompanyDao().setStatusRevoked(company);
			logger.info("exection CompanyRenderingController.changeStatus - chahge status REVOKED");
			break;
		case REVOKED :
			DaoFactory.getCompanyDao().setStatusNotVerified(company);
			logger.info("exection CompanyRenderingController.changeStatus - chahge status NOT_VERIFIED");
			break;
		}
		logger.info("exection CompanyRenderingController.changeStatus successfully finished");
		return getListCompanyAll();
	}

	@RequestMapping("CancelApll.spr")
	public ModelAndView cancelApplication(@RequestParam("id") long id) throws DaoException {
		logger.info("exection CompanyRenderingController.cancelApplication");
		logger.info(id);
		Company company = DaoFactory.getCompanyDao().getById(id);
		if (company.getStatus() == CompanyStatus.REVOKED) {
			DaoFactory.getLotDao().setStatusCancel(company.getId());
			logger.info("exection CompanyRenderingController.cancelApplication - cancel all lots");
			DaoFactory.getTenderDao().setStatusCancel(company.getId());
			logger.info("exection CompanyRenderingController.cancelApplication - cancel all tenders");
		}
		logger.info("exection CompanyRenderingController.cancelApplication successfully finished");
		return getListCompanyAll();

	}
}
