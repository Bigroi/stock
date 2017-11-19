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
public class MailLinkRendeling extends BaseRenderingController{

	private static final String EXPIRED_LINK = "link.exparied";
	private static final String WRONG_LINK = "link.incorrect";
	private static final String RECONFIRMATION = "link.already.activated";
	private static final String APPROVE_MESSAGE = "link.deal.approved";
	private static final String CANCEL_MESSAGE = "link.deal.canceled";

	@RequestMapping("/SellerCheck.spr")
	public ModelAndView sellerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action)
			throws ServiceException {
		ModelAndView modelAndView = createModelAndView("approveLink");
		
		String message = checkSellerLink(id, key, action);
		if (message != null){
			modelAndView.addObject("message", message);
		} else {
			//TODO authorize user
			switch (action) {
			case APPROVE:
				ServiceFactory.getDealService().setApprovedBySeller(id);
				modelAndView.addObject("message", APPROVE_MESSAGE);
			case CANCEL:
				ServiceFactory.getDealService().cancel(id, true);		
				modelAndView.addObject("message", CANCEL_MESSAGE);
			default:
				throw new ServiceException("unknown action: " + action);
			}
		}
		return modelAndView;
	}

	@RequestMapping("/CustomerCheck.spr")
	public ModelAndView customerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action)
			throws ServiceException {
		ModelAndView modelAndView = createModelAndView("approveLink");
		
		String message = checkCustommerLink(id, key, action);
		if (message != null){
			modelAndView.addObject("message", message);
		} else {
			//TODO authorize user
			switch (action) {
			case APPROVE:
				ServiceFactory.getDealService().setApprovedByCustomer(id);
				modelAndView.addObject("message", APPROVE_MESSAGE);
			case CANCEL:
				ServiceFactory.getDealService().cancel(id, false);		
				modelAndView.addObject("message", CANCEL_MESSAGE);
			default:
				throw new ServiceException("unknown action: " + action);
			}
		}
		return modelAndView;
	}
	
	private String checkCustommerLink(long id, String key, Action action) throws ServiceException{
		PreDeal preDeal = ServiceFactory.getDealService().getById(id);
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
	
	private String checkSellerLink(long id, String key, Action action) throws ServiceException{
		PreDeal preDeal = ServiceFactory.getDealService().getById(id);
		if (preDeal == null) {
			return EXPIRED_LINK;
		}
		if (!preDeal.getSellerHashCode().equals(key)) {
			return WRONG_LINK;
		}
		if (preDeal.getSellerApprovBool()) {
			return RECONFIRMATION;
		}
		return null;
	}
}
