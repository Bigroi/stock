package com.bigroi.transport.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.transport.bean.User;
import com.bigroi.transport.dao.DaoException;
import com.bigroi.transport.dao.DaoFactory;

@Controller
public class UserTestController {
	
	@RequestMapping("/Index.spr")
	public ModelAndView goHomepage() {
		return new ModelAndView("main");
	}
	
	@RequestMapping("/list.spr")
	ModelAndView getAllUsers() throws DaoException{
		List<User> list = DaoFactory.getAllUsers().getAll();
		return new ModelAndView("main","listOfUsers", list);
		
	}

}
