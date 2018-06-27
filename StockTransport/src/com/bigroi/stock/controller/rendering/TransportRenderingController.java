package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
public class TransportRenderingController extends BaseRenderingController {

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
		return createModelAndView("propositions");
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
