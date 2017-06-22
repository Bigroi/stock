package com.bigroi.stock.controller;



import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoExeptions;
import com.bigroi.stock.dao.DaoFactory;


@Controller
public class TestControler {
		
	@RequestMapping("/test.spr")
	public ModelAndView test() {
		DriverManagerDataSource datasourse =  (DriverManagerDataSource) DaoFactory.getContext().getBean("datasource");
		return new ModelAndView("test", "datasourse", datasourse);
	}
	
	@RequestMapping("/testAuth.spr")
	public ModelAndView authTesr() {
		
		return test();
	}
	@RequestMapping("/getAllUser.spr")
	public ModelAndView getAll() throws DaoExeptions{
		List<User> users =DaoFactory.getUserDao().getAllUser();
		System.out.println(users);
		return new ModelAndView("test","UserofList",users);
		
	}
	
	
	
	
}
