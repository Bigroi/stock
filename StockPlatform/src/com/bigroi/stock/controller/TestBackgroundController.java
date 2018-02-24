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
		return createModelAndView("testBG");
	} 

	@RequestMapping(value = "/ClearPreDeals.spr")
	public ModelAndView startClearPredeal() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		ServiceFactory.getMarketService().clearPreDeal();
		String message = "ClearPreDeal is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
 
	@RequestMapping(value = "/Trading.spr")
	public ModelAndView startTrade() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		ServiceFactory.getTradeService().trade();
		String message = "Trade is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}

	@RequestMapping(value = "/SendEmails.spr")
	public ModelAndView startSendMail() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		ServiceFactory.getMessageService().sendAllEmails();
		String message = "SendEmails is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/CheckStatus.spr")
	public ModelAndView checkStatus() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		ServiceFactory.getMarketService().checkExparations();
		String message = "CheckStatus is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}

}
