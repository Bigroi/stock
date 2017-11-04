package com.bigroi.stock.controller.rendering;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class MailLinkRendeling {

	private static final String EXPIRED_LINK = "link.exparied";
	private static final String WRONG_LINK = "link.incorrect";
	private static final String RECONFIRMATION = "link.already.activated";
	private static final String APPROVE_MESSAGE = "link.deal.approved";
	private static final String CANCEL_MESSAGE = "link.deal.canceled";

	@RequestMapping("/SellerCheck.spr")
	public ModelAndView sellerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action)
			throws ServiceException {
		
		String message = checkLink(id, key, action);
		if (message != null){
			return new ModelAndView("approveLink", "message", message);
		}
		
		switch (action) {
		case APPROVE:
			ServiceFactory.getPreDealService().setApprovedBySeller(id);
			return new ModelAndView("approveLink", "message", APPROVE_MESSAGE);
		case CANCEL:
			ServiceFactory.getPreDealService().cancel(id, true);		
			return new ModelAndView("approveLink", "message", CANCEL_MESSAGE);
		default:
			throw new ServiceException("unknown action: " + action);
		}
	}

	@RequestMapping("/CustomerCheck.spr")
	public ModelAndView customerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action)
			throws ServiceException {
		
		String message = checkLink(id, key, action);
		if (message != null){
			return new ModelAndView("approveLink", "message", message);
		}
		
		switch (action) {
		case APPROVE:
			ServiceFactory.getPreDealService().setApprovedByCustomer(id);
			return new ModelAndView("approveLink", "message", APPROVE_MESSAGE);
		case CANCEL:
			ServiceFactory.getPreDealService().cancel(id, false);		
			return new ModelAndView("approveLink", "message", CANCEL_MESSAGE);
		default:
			throw new ServiceException("unknown action: " + action);
		}
	}
	
	private String checkLink(long id, String key, Action action) throws ServiceException{
		PreDeal preDeal = ServiceFactory.getPreDealService().getById(id);
		if (preDeal == null) {
			return EXPIRED_LINK;
		}
		if (!preDeal.getCustomerHashCode().equals(key)) {
			return WRONG_LINK;
		}
		if (preDeal.getCustApprovBool()) {
			return RECONFIRMATION;
		}
		return null;
	}
}
