package com.bigroi.stock.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;

@Controller
public class TestControler {
	
	@RequestMapping("/test.spr")
	public ModelAndView listGroups() {
		ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring.xml");
		User user = (User) CONTEX.getBean("testUser");
		return new ModelAndView("test", "user", user);
	}

}
