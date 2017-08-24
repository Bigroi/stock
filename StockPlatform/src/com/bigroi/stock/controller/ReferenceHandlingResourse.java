package com.bigroi.stock.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class ReferenceHandlingResourse extends ResourseBeanException {
	
private static final Logger logger = Logger.getLogger(ReferenceHandling.class);
	
	private PreDeal preDeal;
	private static final String EXPIRED_LINK = "������ ���������� ��� ������� ����� �� �������������� ��������";
	private static final String WRONG_LINK = "�������� ������";
	private static final String RECONFIRMATION = "�� ��� ������������ ������ �����";
	private static final String APPROVE_LINK = "�� ����������� ������";
	private static final String CANCEL_LINK = "��  ���������� �� ������";
	
	@RequestMapping("/SellerCheck.spr")
	@ResponseBody
	public String sellerCheck(@RequestParam("id") long id,
			@RequestParam("jsonPredeal")  String jsonPredeal,
			@RequestParam("key") String key,
			@RequestParam("action") Action action) throws DaoException, IOException, MailManagerException {
		logger.info("exection ReferenceHandling.sellerCheck");
		logger.info(id);
		logger.info(jsonPredeal);
		logger.info(key);
		logger.info(action);
		PreDeal beanPredeal = new Gson().fromJson(jsonPredeal, PreDeal.class);
		Map<String,String> map = new HashMap<>();
		String message = null;
		preDeal = DaoFactory.getPreDealDao().getById(id);
		if (preDeal == null) {
			map.put("message", EXPIRED_LINK);
			return new ResultBean(1, map ).toString();      
		}
		if (!preDeal.getSellerHashCode().equals(key)) {
			map.put("message", WRONG_LINK);
			return new ResultBean(1, map ).toString();   
		}
		if (preDeal.getSellerApprovBool()) {
			map.put("message", RECONFIRMATION);
			return new ResultBean(1, map ).toString(); 
		}

		switch (action) {
		case APPROVE:
			preDeal.setSellerApprovBool(true);
			DaoFactory.getPreDealDao().updateById(beanPredeal);
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
			DaoFactory.getPreDealDao().deletedById(beanPredeal.getId());			
			message = CANCEL_LINK;
			break;
		}
		map.put("message", message);
		logger.info("exection ReferenceHandling.sellerCheck successfully finished");
		return new ResultBean(1, map ).toString();
	}
	
	@RequestMapping("/CustomerCheck.spr")
	@ResponseBody
	public String customerCheck(@RequestParam("id") long id, 
			@RequestParam("jsonPredeal")  String jsonPredeal,
			@RequestParam("key") String key,
			@RequestParam("action") Action action) throws DaoException, IOException, MailManagerException {
		logger.info("exection ReferenceHandling.customerCheck");
		logger.info(id);
		logger.info(key);
		logger.info(action);
		PreDeal beanPredeal = new Gson().fromJson(jsonPredeal, PreDeal.class);
		Map<String,String> map = new HashMap<>();
		String message = null;
		preDeal = DaoFactory.getPreDealDao().getById(id);
		if (preDeal == null) {
			map.put("message", EXPIRED_LINK);
			return new ResultBean(1, map ).toString(); 
		}
		if (!preDeal.getCustomerHashCode().equals(key)) {
			map.put("message", WRONG_LINK);
			return new ResultBean(1, map ).toString(); 
		}
		if (preDeal.getCustApprovBool()) {
			map.put("message", RECONFIRMATION);
			return new ResultBean(1, map ).toString();
		}

		switch (action) {
		case APPROVE:
			preDeal.setCustApprovBool(true);
			DaoFactory.getPreDealDao().updateById(beanPredeal);
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
			DaoFactory.getPreDealDao().deletedById(beanPredeal.getId());			
			message = CANCEL_LINK;
			break;
		}
		logger.info("exection ReferenceHandling.customerCheck successfully finished");
		map.put("message", message);
		return new ResultBean(1, map ).toString();
	}

	private void addBlackList() throws DaoException {
		logger.info("exection ReferenceHandling.addBlackList");
		Blacklist blackList = new Blacklist();
		blackList.setLotId(preDeal.getLotId());
		blackList.setTenderId(preDeal.getTenderId());
		DaoFactory.getBlacklistDao().add(blackList);
		logger.info("exection ReferenceHandling.addBlackList successfully finished");
	}


	private void addDeal() throws DaoException {
		logger.info("exection ReferenceHandling.addDeal");
		Deals deal = new Deals();
		deal.setLotId(preDeal.getLotId());
		deal.setTenderId(preDeal.getTenderId());
		DaoFactory.getDealsDao().add(deal);
		logger.info("exection ReferenceHandling.addDeal successfully finished");
	}

	private void setStatuses(Status status) throws DaoException {
		logger.info("exection ReferenceHandling.setStatuses");
		logger.info(status);
		Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
		lot.setStatus(status);
		DaoFactory.getLotDao().updateById(lot);
		Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
		tender.setStatus(status);
		DaoFactory.getTenderDao().updateById(tender);	
		logger.info("exection ReferenceHandling.setStatuses successfully finished");
	}

}
