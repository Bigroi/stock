package com.bigroi.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.impl.TradeServiceImpl;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseRenderingController {
	
	private static final String TEST_PAGE = "testBG";
	private static final String MESSAGE = "message";
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/Index.spr")
	public ModelAndView showPage(){
		return createModelAndView(TEST_PAGE);
	} 

	@RequestMapping(value = "/ClearPreDeals.spr")
	public ModelAndView startClearPredeal() throws ServiceException {
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		marketService.clearPreDeal();
		String message = "ClearPreDeal is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}
 
	@RequestMapping(value = "/Trading.spr")
	public ModelAndView startTrade() throws ServiceException {
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		new TradeServiceImpl().trade();
		String message = "Trade is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}

	@RequestMapping(value = "/SendEmails.spr")
	public ModelAndView startSendMail() throws ServiceException {
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		messageService.sendAllEmails();
		String message = "SendEmails is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/CheckStatus.spr")
	public ModelAndView checkStatus() throws ServiceException {
		ModelAndView modelAndView = createModelAndView(TEST_PAGE);
		
		marketService.checkExparations();
		String message = "CheckStatus is finished";
		modelAndView.addObject(MESSAGE, message);
		
		return modelAndView;
	}

}
