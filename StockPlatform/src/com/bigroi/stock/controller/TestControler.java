package com.bigroi.stock.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;


@Controller
public class TestControler {
		
	@RequestMapping("/test.spr")
	public ModelAndView listGroups() {
		User user = (User) AppContext.getContext().getBean("testUser");
		return new ModelAndView("test", "user", user);
	}
}
