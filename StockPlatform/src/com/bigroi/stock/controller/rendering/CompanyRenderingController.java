package com.bigroi.stock.controller.rendering;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/company/admin")
public class CompanyRenderingController extends BaseRenderingController{
	
	@RequestMapping("/List.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView getListCompanyAll() throws ServiceException {
		return createModelAndView("companies");
	}

}
