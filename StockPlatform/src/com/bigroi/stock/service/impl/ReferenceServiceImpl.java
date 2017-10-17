package com.bigroi.stock.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.controller.ReferenceHandling;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.dao.DealsDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ReferenceService;
import com.bigroi.stock.service.ServiceException;

public class ReferenceServiceImpl implements ReferenceService{
	
	private static final Logger logger = Logger.getLogger(ReferenceServiceImpl.class);
	
	private PreDealDao preDealDao;
	private BlacklistDao blacklistDao;
	private DealsDao dealsDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
	private static final String APPROVE_LINK = "Вы подтвердили сделку";
	private static final String CANCEL_LINK = "Вы  отказались от сделки";
	
	public void setPreDealDao(PreDealDao preDealDao) {
		this.preDealDao = preDealDao;
	}
	
	public void setBlacklistDao(BlacklistDao blacklistDao) {
		this.blacklistDao = blacklistDao;
	}
	
	public void setDealsDao(DealsDao dealsDao) {
		this.dealsDao = dealsDao;
	}
	
	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}
	
	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}
	
	@Override
	@Transactional
	public String callSellerCheck(long id, String key, Action action) throws ServiceException {
		String message = "";
		switch (action) {
		case APPROVE:
			ReferenceHandling.preDeal.setSellerApprovBool(true);
			try{
				preDealDao.updateById(ReferenceHandling.preDeal);
			if (ReferenceHandling.preDeal.getCustApprovBool()) {				
				addDeal();				
				setStatuses(Status.SUCCESS);				
				new Message().sendMessageSuccess(ReferenceHandling.preDeal);;
			}
			}catch(DaoException | IOException | MailManagerException e){
				MessagerFactory.getMailManager().sendToAdmin(e);
			}
			message = APPROVE_LINK;
			break;

		case CANCEL:
			try{
			addBlackList();
			setStatuses(Status.IN_GAME);			
			new Message().sendMessageCancelSeller(ReferenceHandling.preDeal);			
			preDealDao.deletedById(ReferenceHandling.preDeal.getId());	
			}catch(DaoException | IOException | MailManagerException e){
				MessagerFactory.getMailManager().sendToAdmin(e);
			}
			message = CANCEL_LINK;
			break;
		}
		return message;
	}

	@Override
	@Transactional
	public PreDeal getByIdPreDeal(long id) throws ServiceException {
		try {
			PreDeal pred = preDealDao.getById(id);
			return pred;
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public String callCustomerCheck(long id, String key, Action action) throws ServiceException {
		String message = "";
		switch (action) {
		case APPROVE:
			ReferenceHandling.preDeal.setCustApprovBool(true);
			try{
				preDealDao.updateById(ReferenceHandling.preDeal);
			if (ReferenceHandling.preDeal.getSellerApprovBool()) {				
				addDeal();				
				setStatuses(Status.SUCCESS);				
				new Message().sendMessageSuccess(ReferenceHandling.preDeal);;
			}
			message = APPROVE_LINK;
			break;
			}catch (DaoException | IOException | MailManagerException e) {
				MessagerFactory.getMailManager().sendToAdmin(e);
			}

		case CANCEL:
			try{
			addBlackList();
			setStatuses(Status.IN_GAME);			
			new Message().sendMessageCancelSeller(ReferenceHandling.preDeal);
			preDealDao.deletedById(ReferenceHandling.preDeal.getId());			
			message = CANCEL_LINK;
			break;
			}catch (DaoException | IOException | MailManagerException e) {
				MessagerFactory.getMailManager().sendToAdmin(e);
			}
		}
		return message;
	}
	
	@Transactional
	private void addBlackList() throws DaoException {
		logger.info("exection ReferenceHandling.addBlackList");
		Blacklist blackList = new Blacklist();
		blackList.setLotId(ReferenceHandling.preDeal.getLotId());
		blackList.setTenderId(ReferenceHandling.preDeal.getTenderId());
		blacklistDao.add(blackList);
		logger.info("exection ReferenceHandling.addBlackList successfully finished");
	}

	@Transactional
	private void addDeal() throws DaoException {
		logger.info("exection ReferenceHandling.addDeal");
		Deals deal = new Deals();
		deal.setLotId(ReferenceHandling.preDeal.getLotId());
		deal.setTenderId(ReferenceHandling.preDeal.getTenderId());
		dealsDao.add(deal);
		logger.info("exection ReferenceHandling.addDeal successfully finished");
	}

	@Transactional
	private void setStatuses(Status status) throws DaoException {
		logger.info("exection ReferenceHandling.setStatuses");
		logger.info(status);
		Lot lot = DaoFactory.getLotDao().getById(ReferenceHandling.preDeal.getLotId());
		lot.setStatus(status);
		lotDao.updateById(lot);
		Tender tender = DaoFactory.getTenderDao().getById(ReferenceHandling.preDeal.getTenderId());
		tender.setStatus(status);
		tenderDao.updateById(tender);	
		logger.info("exection ReferenceHandling.setStatuses successfully finished");
	}
	
	

}
