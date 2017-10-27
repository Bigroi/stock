package com.bigroi.stock.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/comapnyAdmin")
public class CompanyRenderingController {
	
	private static final Logger logger = Logger.getLogger(CompanyRenderingController.class);

	@RequestMapping("/CompanyList.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView getListCompanyAll() throws ServiceException {
		logger.info("exection CompanyRenderingController.getListCompanyAll");
		List<Company> list = ServiceFactory.getCompanyService().getAllCompsny();
		logger.info("exection CompanyRenderingController.getListCompanyAll successfully finished");
		return new ModelAndView("companyList", "listOfCompany", list);
	}

	@RequestMapping("/ChangeStatus.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws ServiceException {
		logger.info("exection CompanyRenderingController.changeStatus");
		logger.info(id);
		ServiceFactory.getCompanyService().changeStatusCompany(id);
		logger.info("exection CompanyRenderingController.changeStatus successfully finished");
		return getListCompanyAll();
	}

	@RequestMapping("CancelApll.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView cancelApplication(@RequestParam("id") long id) throws ServiceException {
		logger.info("exection CompanyRenderingController.cancelApplication");
		logger.info(id);
		ServiceFactory.getCompanyService().statusCancelLotAndTender(id);
		logger.info("exection CompanyRenderingController.cancelApplication successfully finished");
		return getListCompanyAll();

	}
}
