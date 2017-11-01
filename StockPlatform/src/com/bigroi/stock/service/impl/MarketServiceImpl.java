package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.ServiceException;

public class MarketServiceImpl implements MarketService {

	private LotDao lotDao;
	
	private TenderDao tenderDao;

	private PreDealDao preDealDao;
	
	public void setPreDealDao(PreDealDao preDealDao) {
		this.preDealDao = preDealDao;
	}
	
	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}
	
	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}
	
	@Override
	@Transactional
	public void setExparation() throws ServiceException{
		try {
			List<Lot> lots = lotDao.getActive();
			for (Lot lot : lots) {
				if (lot.isExpired()){
					lotDao.setStatusById(lot.getId(), Status.EXPIRED);
				}
			}
			List<Tender> tenders = tenderDao.getAllActive();
			for (Tender tender : tenders) {
				if (tender.isExpired()){
					tenderDao.setStatusById(tender.getId(), Status.EXPIRED);
				}
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void clearPreDeal() throws ServiceException {
		try {
			List<PreDeal> predials = preDealDao.getAll();
			for (PreDeal preDeal : predials) {
				if (preDeal.getSellerApprovBool() && preDeal.getCustApprovBool())
					continue;
				setStatuses(preDeal);
				new Message().sendMessageClearPredeal(preDeal);
			}
			preDealDao.deleteAll();
		} catch (DaoException | MailManagerException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
	
	private void setStatuses(PreDeal predeal) throws DaoException {
		Lot lot = lotDao.getById(predeal.getLotId());
		if (lot.isExpired()) {
			lotDao.setStatusById(lot.getId(), Status.EXPIRED);
		} else {
			lotDao.setStatusById(lot.getId(), Status.IN_GAME);
		}
		Tender tender = tenderDao.getById(predeal.getTenderId());
		if (tender.isExpired()) {
			tenderDao.setStatusById(tender.getId(), Status.EXPIRED);
		} else {
			tenderDao.setStatusById(tender.getId(), Status.IN_GAME);
		}
	}

	@Override
	public void trade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendConfimationMails() throws ServiceException{
		try {
			List<PreDeal> preDials = preDealDao.getAll();
			for (PreDeal preDeal : preDials) {
				lotDao.setStatusById(preDeal.getLotId(), Status.ON_APPROVAL);
				tenderDao.setStatusById(preDeal.getTenderId(), Status.ON_APPROVAL);
				new Message().sendMessageForConfirmation(preDeal);
			}
		} catch (DaoException | MailManagerException e) {
			throw new ServiceException(e);
		}
	}
}
