package com.bigroi.stock.controller.rendering;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
@RequestMapping("/company/admin")
public class CompanyRenderingController extends BaseRenderingController{
	
	@RequestMapping("/List")
	@Secured("ROLE_ADMIN")
	public ModelAndView getListCompanyAll() {
		return createModelAndView("companies");
	}

}
