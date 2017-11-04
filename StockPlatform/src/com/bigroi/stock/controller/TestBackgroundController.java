package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.resource.BaseResourseController;
import com.bigroi.stock.jobs.CheckStatus;
import com.bigroi.stock.jobs.ClearPreDeal;
import com.bigroi.stock.jobs.SendEmail;
import com.bigroi.stock.jobs.Trading;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseResourseController {
	
	@RequestMapping(value = "/Index.spr")
	public String showPage(){
		return "test/testBackgroundFunctions";
	} 

	@RequestMapping(value = "/ClearPreDeals.spr")
	public ModelAndView startClearPredeal() {
		new ClearPreDeal().run();
		String message = "ClearPreDeal().run() is finished";
		return new ModelAndView("test/testBackgroundFunctions", "message", message);
	}
 
	@RequestMapping(value = "/Trading.spr")
	public ModelAndView startTrade() {
		new Trading().run();
		String message = "Trade().run() is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}

	@RequestMapping(value = "/SendEmails.spr")
	public ModelAndView startSendMail() {
		new SendEmail().run();
		String message = "SendEmail().run() is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}
	
	@RequestMapping(value = "/CheckStatus.spr")
	public ModelAndView checkStatus() {
		new CheckStatus().run();
		String message = "CheckStatus().run() is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}
}
