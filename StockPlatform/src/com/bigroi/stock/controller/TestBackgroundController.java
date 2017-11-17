package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.resource.BaseResourseController;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseResourseController {
	
	@RequestMapping(value = "/Index.spr")
	public String showPage(){
		return "test/testBackgroundFunctions";
	} 

	@RequestMapping(value = "/ClearPreDeals.spr")
	public ModelAndView startClearPredeal() throws ServiceException {
		ServiceFactory.getMarketService().clearPreDeal();
		String message = "ClearPreDeal is finished";
		return new ModelAndView("test/testBackgroundFunctions", "message", message);
	}
 
	@RequestMapping(value = "/Trading.spr")
	public ModelAndView startTrade() throws ServiceException {
		ServiceFactory.getTradeService().trade();
		String message = "Trade is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}

	@RequestMapping(value = "/SendEmails.spr")
	public ModelAndView startSendMail() throws ServiceException {
		ServiceFactory.getMessageService().sendAllEmails();
		String message = "SendEmails is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}
	
	@RequestMapping(value = "/CheckStatus.spr")
	public ModelAndView checkStatus() throws ServiceException {
		ServiceFactory.getMarketService().checkExparations();
		String message = "CheckStatus is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}
	
	@RequestMapping(value = "/sendConfMessages.spr")
	public ModelAndView sendConfMessages() throws ServiceException {
		ServiceFactory.getMarketService().checkExparations();
		String message = "sendConfMessages is finished";
		return new ModelAndView("test/testBackgroundFunctions",  "message", message);
	}
}
