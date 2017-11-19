package com.bigroi.stock.controller.rendering;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/company/admin")
public class CompanyRenderingController extends BaseRenderingController{
	
	@RequestMapping("/List.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView getListCompanyAll() throws ServiceException {
		return createModelAndView("companies");
	}

	//TODO move to js
	@RequestMapping("/ChangeStatus.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView changeStatus(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getCompanyService().changeStatusCompany(id);
		return getListCompanyAll();
	}

}
