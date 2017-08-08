package com.bigroi.stock.controller;

import java.util.List;

import javax.xml.ws.soap.AddressingFeature.Responses;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.google.gson.Gson;

@Controller
public class TestRenderingController {

	/*private Gson gson = new Gson();

	@RequestMapping(value = "/testContrJSON.spr", method = RequestMethod.GET)
	public ModelAndView getListUserTest() throws DaoException{
		List<User> list = DaoFactory.getUserDao().getAllUser();
		gson.toJson(list);
		return new ModelAndView("testJspJSON","listofUsers",gson);
		
	}*/
	
	@RequestMapping(value = "/testContrJSON.spr",
                    method = RequestMethod.GET, //if POST --> HTTP Status 405 - Request method 'GET' not supported
                    headers="application/json" )
	@ResponseBody
	public List<User> getListUserTest() throws DaoException{
		List<User> list = DaoFactory.getUserDao().getAllUser();
			return list;
	}
	
	
}
