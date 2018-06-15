package com.bigroi.stock.controller.rendering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.DealService;

@Controller
public class TransportRenderingController extends BaseRenderingController {
	
	@Autowired
	private DealService dealService;

	@RequestMapping("index.spr")
	private ModelAndView index() throws Exception {
		return createModelAndView("index");
	}
	
	@RequestMapping("account.spr")
	private ModelAndView account() throws Exception {
		return createModelAndView("account");
	}
	
	@RequestMapping("registration.spr")
	private ModelAndView registration() throws Exception {
		return createModelAndView("registation");
	}
	
	@RequestMapping("propositions")
	private ModelAndView propositions() throws Exception {
		return createModelAndView("propositions");
	}
	
	@RequestMapping("deal-list.spr")
	private ModelAndView dealList() throws Exception {
		ModelAndView modelAndView = createModelAndView("deal-list");
		modelAndView.addObject("ListOfDealsBySellerAndBuyerApproved", "hh");
				//dealService.getListBySellerAndBuyerApproved());
		return modelAndView;
	}
	
	@RequestMapping("history.spr")
	private ModelAndView history() throws Exception {
		return createModelAndView("history");
	}
	
	@RequestMapping("login.spr")
	private ModelAndView login() throws Exception {
		return createModelAndView("login");
	}
	
	@RequestMapping("/main.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView main() {
		return createModelAndView("main");
	}
}
