package com.bigroi.stock.controller.rendering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.PropositionService;

@Controller
public class TransportRenderingController extends BaseRenderingController {
	
	@Autowired
	private PropositionService propService;

	@RequestMapping("index.spr")
	public ModelAndView index() throws Exception {
		return createModelAndView("index");
	}
	
	@RequestMapping("account.spr")
	public ModelAndView account() throws Exception {
		return createModelAndView("account");
	}
	
	@RequestMapping("registration.spr")
	public ModelAndView registration() throws Exception {
		return createModelAndView("registation");
	}
	
	@RequestMapping("propositions")
	public ModelAndView propositions() throws Exception {
		ModelAndView modelAndView = createModelAndView("propositions");
		modelAndView.addObject("listOfPropositions",
				propService.getListProposition());
		return modelAndView;
	}
	
	@RequestMapping("deal-list.spr")
	public ModelAndView dealList() throws Exception {
		return createModelAndView("deal-list");
	}
	
	@RequestMapping("history.spr")
	public ModelAndView history() throws Exception {
		return createModelAndView("history");
	}
	
	@RequestMapping("login.spr")
	public ModelAndView login() throws Exception {
		return createModelAndView("login");
	}
	
	@RequestMapping("/main.spr")
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView main() {
		return createModelAndView("main");
	}
}
