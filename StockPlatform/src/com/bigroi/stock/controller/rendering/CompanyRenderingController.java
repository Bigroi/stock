package com.bigroi.stock.controller.rendering;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/company/admin")
public class CompanyRenderingController extends BaseRenderingController{
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("/List.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView getListCompanyAll() throws ServiceException {
		return createModelAndView("companies");
	}

	//TODO move to js
	@RequestMapping("/ChangeStatus.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws ServiceException {
		companyService.changeStatusCompany(id);
		return getListCompanyAll();
	}

}
