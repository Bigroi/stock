package com.bigroi.stock.controller;



import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.dao.DaoFactory;


@Controller
public class TestControler {
		
	@RequestMapping("/test.spr")
	public ModelAndView listGroups() {
		DriverManagerDataSource datasourse =  (DriverManagerDataSource) DaoFactory.getContext().getBean("datasource");
		return new ModelAndView("test", "datasourse", datasourse);
	}
}
