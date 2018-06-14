package com.bigroi.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TradeService;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseRenderingController {
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/Index.spr")
	public ModelAndView showPage(){
		return createModelAndView("testBG");
	} 

	@RequestMapping(value = "/ClearPreDeals.spr")
	public ModelAndView startClearPredeal() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		marketService.clearPreDeal();
		String message = "ClearPreDeal is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
 
	@RequestMapping(value = "/Trading.spr")
	public ModelAndView startTrade() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		tradeService.trade();
		String message = "Trade is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}

	@RequestMapping(value = "/SendEmails.spr")
	public ModelAndView startSendMail() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		messageService.sendAllEmails();
		String message = "SendEmails is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/CheckStatus.spr")
	public ModelAndView checkStatus() throws ServiceException {
		ModelAndView modelAndView = createModelAndView("testBG");
		
		marketService.checkExparations();
		String message = "CheckStatus is finished";
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}

}
