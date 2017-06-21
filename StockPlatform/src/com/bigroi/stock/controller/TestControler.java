package com.bigroi.stock.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoFactory;


@Controller
public class TestControler {
		
	@RequestMapping("/test.spr")
	public ModelAndView listGroups() {
		User user = (User) DaoFactory.getContext().getBean("testUser");
		return new ModelAndView("test", "user", user);
	}
}
