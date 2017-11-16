package com.bigroi.stock.controller.rendering;


import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/company/admin")
public class CompanyRenderingController {
	
	@RequestMapping("/List.spr")
	@Secured("ADMIN")
	public ModelAndView getListCompanyAll() throws ServiceException {
		List<Company> list = ServiceFactory.getCompanyService().getAllCompanies();
		return new ModelAndView("companyList", "listOfCompany", list);
	}

	@RequestMapping("/ChangeStatus.spr")
	@Secured("ADMIN")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getCompanyService().changeStatusCompany(id);
		return getListCompanyAll();
	}

}
