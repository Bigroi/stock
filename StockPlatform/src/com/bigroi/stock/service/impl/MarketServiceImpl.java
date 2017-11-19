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
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
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
	public void checkExparations() throws ServiceException{
		try {
			List<Lot> lots = lotDao.getActive();
			for (Lot lot : lots) {
				if (lot.isExpired()){
					lot.setStatus(Status.EXPIRED);
					Message<Lot> message = MessagerFactory.getLotExparationMessage();
					message.setDataObject(lot);
					message.send();
				}
			}
			lotDao.update(lots);
			
			List<Tender> tenders = tenderDao.getAllActive();
			for (Tender tender : tenders) {
				if (tender.isExpired()){
					tender.setStatus(Status.EXPIRED);
					Message<Tender> message = MessagerFactory.getTenderExparationMessage();
					message.setDataObject(tender);
					message.send();
				}
			}
			tenderDao.update(tenders);
		} catch (DaoException | MessageException e) {
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
					throw new ServiceException("Deal approved by both sides. It should not be in preDeal.");
				else if (preDeal.getSellerApprovBool()){
					Message<PreDeal> message = MessagerFactory.getDealExparationMessageForSellerByOpponent();
					message.setDataObject(preDeal);
					message.send();
					
					message = MessagerFactory.getDealExparationMessageForCustomer();
					message.setDataObject(preDeal);
					message.send();
				} else if (preDeal.getCustApprovBool()){
					Message<PreDeal> message = MessagerFactory.getDealExparationMessageForSeller();
					message.setDataObject(preDeal);
					message.send();
					
					message = MessagerFactory.getDealExparationMessageForCustomerByOpponent();
					message.setDataObject(preDeal);
					message.send();
				} else {
					Message<PreDeal> message = MessagerFactory.getDealExparationMessageForSeller();
					message.setDataObject(preDeal);
					message.send();
					
					message = MessagerFactory.getDealExparationMessageForCustomer();
					message.setDataObject(preDeal);
					message.send();
				}
				setStatuses(preDeal);
			}
			preDealDao.deleteAll();
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
	
	private void setStatuses(PreDeal predeal) throws DaoException {
		Lot lot = lotDao.getById(predeal.getLotId());
		if (lot.isExpired()) {
			lot.setStatus(Status.EXPIRED);
		} 
		lot.setVolume(lot.getVolume() + predeal.getVolume());
		lotDao.update(lot);
		
		Tender tender = tenderDao.getById(predeal.getTenderId());
		if (tender.isExpired()) {
			tender.setStatus(Status.EXPIRED);
		} 
		tender.setVolume(tender.getVolume() + predeal.getVolume());
		tenderDao.update(tender);
	}

	@Override
	@Transactional
	public void sendConfirmationMessages() throws ServiceException {
		try {
			List<PreDeal> preDials = preDealDao.getAll();
			for (PreDeal preDeal : preDials) {
				Message<PreDeal> message = MessagerFactory.getDealConfirmationMessageForCustomer();
				message.setDataObject(preDeal);
				message.send();
				
				message = MessagerFactory.getDealConfirmationMessageForSeller();
				message.setDataObject(preDeal);
				message.send();
			}
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
}
