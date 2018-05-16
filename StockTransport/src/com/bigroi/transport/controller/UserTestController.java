package com.bigroi.transport.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.transport.bean.User;
import com.bigroi.transport.service.ServiceException;
import com.bigroi.transport.service.ServiceFactory;

@Controller
public class UserTestController {
	
	@RequestMapping("/Index.spr")
	public ModelAndView goHomepage() {
		return new ModelAndView("main");
	}
	
	@RequestMapping("/list.spr")
	ModelAndView getAllUsers() throws ServiceException{
		List<User> list = ServiceFactory.getUserService().getAll();
		return new ModelAndView("main","listOfUsers", list);
		
	}

}
