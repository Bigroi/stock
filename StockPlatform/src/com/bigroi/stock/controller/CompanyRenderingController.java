package com.bigroi.stock.controller;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

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
	public ModelAndView changeStatus(@RequestParam("id") long id) throws DaoException, ServiceException {
		logger.info("exection CompanyRenderingController.changeStatus");
		logger.info(id);
		ServiceFactory.getCompanyService().changeStatusCompany(id);
		logger.info("exection CompanyRenderingController.changeStatus successfully finished");
		return getListCompanyAll();
	}

	@RequestMapping("CancelApll.spr")
	public ModelAndView cancelApplication(@RequestParam("id") long id) throws DaoException, ServiceException {
		logger.info("exection CompanyRenderingController.cancelApplication");
		logger.info(id);
		ServiceFactory.getCompanyService().statusCancelLotAndTender(id);
		logger.info("exection CompanyRenderingController.cancelApplication successfully finished");
		return getListCompanyAll();

	}
}
