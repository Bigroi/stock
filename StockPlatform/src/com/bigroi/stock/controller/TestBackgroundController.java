package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.rendering.BaseRenderingController;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseRenderingController {
	
	@RequestMapping(value = "/Index.spr")
	public ModelAndView showPage(){
		return createModelAndView("testBackgroundFunctions");
	} 

	@RequestMapping(value = "/ClearPreDeals.spr")
	public ModelAndView startClearPredeal() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBackgroundFunctions");
		
		ServiceFactory.getMarketService().clearPreDeal();
		String message = "ClearPreDeal is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
 
	@RequestMapping(value = "/Trading.spr")
	public ModelAndView startTrade() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBackgroundFunctions");
		
		ServiceFactory.getTradeService().trade();
		String message = "Trade is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}

	@RequestMapping(value = "/SendEmails.spr")
	public ModelAndView startSendMail() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBackgroundFunctions");
		
		ServiceFactory.getMessageService().sendAllEmails();
		String message = "SendEmails is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/CheckStatus.spr")
	public ModelAndView checkStatus() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBackgroundFunctions");
		
		ServiceFactory.getMarketService().checkExparations();
		String message = "CheckStatus is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/sendConfMessages.spr")
	public ModelAndView sendConfMessages() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBackgroundFunctions");
		
		ServiceFactory.getMarketService().checkExparations();
		String message = "sendConfMessages is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
}
