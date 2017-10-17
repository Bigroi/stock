package com.bigroi.stock.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class ReferenceHandling {

	private static final Logger logger = Logger.getLogger(ReferenceHandling.class);

	public static PreDeal preDeal;
	
	private static final String EXPIRED_LINK = "—сылка просрочена или получен отказ от потенциального партнера";
	private static final String WRONG_LINK = "Ќеверна€ ссылка";
	private static final String RECONFIRMATION = "¬ы уже подтверждали сделку ранее";

	@RequestMapping("/SellerCheck.spr")
	public ModelAndView sellerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action)
			throws ServiceException {
		logger.info("exection ReferenceHandling.sellerCheck");
		logger.info(id);
		logger.info(key);
		logger.info(action);
		preDeal = ServiceFactory.getReferenceService().getByIdPreDeal(id);
		String message = ServiceFactory.getReferenceService().callSellerCheck(id, key, action);
		if (preDeal == null) {
			return new ModelAndView("approveLink", "message", EXPIRED_LINK);
		}
		if (!preDeal.getSellerHashCode().equals(key)) {
			return new ModelAndView("approveLink", "message", WRONG_LINK);
		}
		if (preDeal.getSellerApprovBool()) {
			return new ModelAndView("approveLink", "message", RECONFIRMATION);
		}
		logger.info("exection ReferenceHandling.sellerCheck successfully finished");
		return new ModelAndView("approveLink", "message", message);
	}

	@RequestMapping("/CustomerCheck.spr")
	public ModelAndView customerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action)
			throws ServiceException {
		logger.info("exection ReferenceHandling.customerCheck");
		logger.info(id);
		logger.info(key);
		logger.info(action);
		preDeal = ServiceFactory.getReferenceService().getByIdPreDeal(id);
		String message = ServiceFactory.getReferenceService().callCustomerCheck(id, key, action);
		if (preDeal == null) {
			return new ModelAndView("approveLink", "message", EXPIRED_LINK);
		}
		if (!preDeal.getCustomerHashCode().equals(key)) {
			return new ModelAndView("approveLink", "message", WRONG_LINK);
		}
		if (preDeal.getCustApprovBool()) {
			return new ModelAndView("approveLink", "message", RECONFIRMATION);
		}
		logger.info("exection ReferenceHandling.customerCheck successfully finished");
		return new ModelAndView("approveLink", "message", message);
	}
}
