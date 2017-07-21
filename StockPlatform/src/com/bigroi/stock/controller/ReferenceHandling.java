package com.bigroi.stock.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;

@Controller
public class ReferenceHandling {
	private PreDeal preDeal;
	private static final String EXPIRED_LINK = "Ссылка просрочена или получен отказ от потенциального партнера";
	private static final String WRONG_LINK = "Неверная ссылка";
	private static final String RECONFIRMATION = "Вы уже подтверждали сделку ранее";
	private static final String APPROVE_LINK = "Вы подтвердили сделку";
	private static final String CANCEL_LINK = "Вы  отказались от сделки";
	
	@RequestMapping("/SellerCheck.spr")
	public ModelAndView sellerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action) throws DaoException, IOException, MailManagerException {
		String message = null;
		preDeal = DaoFactory.getPreDealDao().getById(id);
		if (preDeal == null) {
			return new ModelAndView("approveLink", "message", EXPIRED_LINK);
		}
		if (!preDeal.getSellerHashCode().equals(key)) {
			return new ModelAndView("approveLink", "message", WRONG_LINK);
		}
		if (preDeal.getSellerApprovBool()) {
			return new ModelAndView("approveLink", "message", RECONFIRMATION);
		}

		switch (action) {
		case APPROVE:
			preDeal.setSellerApprovBool(true);
			DaoFactory.getPreDealDao().updateById(preDeal);
			if (preDeal.getCustApprovBool()) {				
				addDeal();				
				setStatuses(Status.SUCCESS);				
				new Message().sendMessageSuccess(preDeal);;
			}
			message = APPROVE_LINK;
			break;

		case CANCEL:
			addBlackList();
			setStatuses(Status.IN_GAME);			
			new Message().sendMessageCancelSeller(preDeal);			
			DaoFactory.getPreDealDao().deletedById(preDeal.getId());			
			message = CANCEL_LINK;
			break;
		}
		return new ModelAndView("approveLink", "message", message);
	}
	
	@RequestMapping("/CustomerCheck.spr")
	public ModelAndView customerCheck(@RequestParam("id") long id, @RequestParam("key") String key,
			@RequestParam("action") Action action) throws DaoException, IOException, MailManagerException {
		String message = null;
		preDeal = DaoFactory.getPreDealDao().getById(id);
		if (preDeal == null) {
			return new ModelAndView("approveLink", "message", EXPIRED_LINK);
		}
		if (!preDeal.getCustomerHashCode().equals(key)) {
			return new ModelAndView("approveLink", "message", WRONG_LINK);
		}
		if (preDeal.getCustApprovBool()) {
			return new ModelAndView("approveLink", "message", RECONFIRMATION);
		}

		switch (action) {
		case APPROVE:
			preDeal.setCustApprovBool(true);
			DaoFactory.getPreDealDao().updateById(preDeal);
			if (preDeal.getSellerApprovBool()) {				
				addDeal();				
				setStatuses(Status.SUCCESS);				
				new Message().sendMessageSuccess(preDeal);;
			}
			message = APPROVE_LINK;
			break;

		case CANCEL:
			addBlackList();
			setStatuses(Status.IN_GAME);			
			new Message().sendMessageCancelSeller(preDeal);
			DaoFactory.getPreDealDao().deletedById(preDeal.getId());			
			message = CANCEL_LINK;
			break;
		}
		return new ModelAndView("approveLink", "message", message);
	}

	private void addBlackList() throws DaoException {
		Blacklist blackList = new Blacklist();
		blackList.setLotId(preDeal.getLotId());
		blackList.setTenderId(preDeal.getTenderId());
		DaoFactory.getBlacklistDao().add(blackList);
	}


	private void addDeal() throws DaoException {
		Deals deal = new Deals();
		deal.setLotId(preDeal.getLotId());
		deal.setTenderId(preDeal.getTenderId());
		DaoFactory.getDealsDao().add(deal);
		
	}

	private void setStatuses(Status status) throws DaoException {
		Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
		lot.setStatus(status);
		DaoFactory.getLotDao().updateById(lot);
		Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
		tender.setStatus(status);
		DaoFactory.getTenderDao().updateById(tender);		
	}
}
