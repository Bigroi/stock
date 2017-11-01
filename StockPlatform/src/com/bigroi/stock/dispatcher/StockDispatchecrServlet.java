package com.bigroi.stock.dispatcher;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class StockDispatchecrServlet extends DispatcherServlet{

	private static final long serialVersionUID = 3544481693328018205L;

	@SuppressWarnings("resource")
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		new ClassPathXmlApplicationContext("spring-timer.xml");
	}
	
}