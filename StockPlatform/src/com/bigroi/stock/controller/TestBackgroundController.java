package com.bigroi.stock.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.market.ClearPreDeal;
import com.bigroi.stock.market.Trade;
import com.bigroi.stock.messager.SendEmail;

@Controller
public class TestBackgroundController extends ResourseBeanException {
	
	@RequestMapping(value = "/testBackgroundFunctions.spr")
	public ModelAndView showPage(){
		return new ModelAndView("testBackgroundFunctions");
	} 

	@RequestMapping(value = "/StartClearPreDeal.spr")
	public ModelAndView startClearPredeal() {
		new ClearPreDeal().run();
		String message = "ClearPreDeal().run() is finished";
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		return new ModelAndView("testBackgroundFunctions", map);
	}
 
	@RequestMapping(value = "/StartTrade.spr")
	public ModelAndView startTrade() {
		new Trade().run();
		String message = "Trade().run() is finished";
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		return new ModelAndView("testBackgroundFunctions", map);
	}

	@RequestMapping(value = "/StartSendEmail.spr")
	public ModelAndView startSendMail() {
		new SendEmail().run();
		String message = "SendEmail().run() is finished";
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		return new ModelAndView("testBackgroundFunctions", map);
	}
}
