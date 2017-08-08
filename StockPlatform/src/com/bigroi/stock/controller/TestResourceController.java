package com.bigroi.stock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.google.gson.Gson;

@Controller
public class TestResourceController {

	private Gson gson = new Gson();
	
	@RequestMapping(value = "/testContrJSON.spr" )
	@ResponseBody
	public String getListUserTest() throws DaoException{
		List<User> list = DaoFactory.getUserDao().getAllUser();
		return gson.toJson(list);
	}
	
	
}
