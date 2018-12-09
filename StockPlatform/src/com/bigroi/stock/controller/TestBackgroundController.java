package com.bigroi.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.TradeService;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseRenderingController {
	
	private static final String TEST_PAGE = "testBG";
	private static final String MESSAGE = "message";
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/Index")
	public ModelAndView showPage(){
		return createModelAndView(TEST_PAGE);
	} 

	@RequestMapping(value = "/ClearPreDeals")
	public ModelAndView startClearPredeal(){
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		marketService.clearPreDeal();
		String message = "ClearPreDeal is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}
 
	@RequestMapping(value = "/Trading")
	public ModelAndView startTrade(){
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		tradeService.newInstance().trade();
		String message = "Trade is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}

	@RequestMapping(value = "/SendEmails")
	public ModelAndView startSendMail(){
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		messageService.sendAllEmails();
		String message = "SendEmails is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/CheckStatus")
	public ModelAndView checkStatus(){
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		marketService.checkExparations();
		String message = "CheckStatus is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}

}
