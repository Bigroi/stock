package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.ServiceException;

public class MarketServiceImpl implements MarketService {

	private LotDao lotDao;
	
	private TenderDao tenderDao;

	private DealDao dealDao;
	
	public void setDealDao(DealDao dealDao) {
		this.dealDao = dealDao;
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
					lot.setStatus(BidStatus.INACTIVE);
					Message<Lot> message = MessagerFactory.getLotExparationMessage();
					message.setDataObject(lot);
					message.send();
				}
			}
			lotDao.update(lots);
			
			List<Tender> tenders = tenderDao.getAllActive();
			for (Tender tender : tenders) {
				if (tender.isExpired()){
					tender.setStatus(BidStatus.INACTIVE);
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
			List<Deal> deals = dealDao.getOnApprove();
			for (Deal deal : deals) {
				if (deal.getSellerApprovedBool() != null){
					Message<Deal> message = MessagerFactory.getDealExparationMessageForSellerByOpponent();
					message.setDataObject(deal);
					message.send();
					
					message = MessagerFactory.getDealExparationMessageForCustomer();
					message.setDataObject(deal);
					message.send();
				} else if (deal.getCustomerApprovedBool() != null){
					Message<Deal> message = MessagerFactory.getDealExparationMessageForSeller();
					message.setDataObject(deal);
					message.send();
					
					message = MessagerFactory.getDealExparationMessageForCustomerByOpponent();
					message.setDataObject(deal);
					message.send();
				} else {
					Message<Deal> message = MessagerFactory.getDealExparationMessageForSeller();
					message.setDataObject(deal);
					message.send();
					
					message = MessagerFactory.getDealExparationMessageForCustomer();
					message.setDataObject(deal);
					message.send();
				}
				setStatuses(deal);
			}
			dealDao.deleteOnApprove();
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
	
	private void setStatuses(Deal deal) throws DaoException {
		Lot lot = lotDao.getById(deal.getLotId());
		if (lot.isExpired()) {
			lot.setStatus(BidStatus.INACTIVE);
		} 
		lot.setMaxVolume(lot.getMaxVolume() + deal.getVolume());
		lotDao.update(lot);
		
		Tender tender = tenderDao.getById(deal.getTenderId());
		if (tender.isExpired()) {
			tender.setStatus(BidStatus.INACTIVE);
		} 
		tender.setMaxVolume(tender.getMaxVolume() + deal.getVolume());
		tenderDao.update(tender);
	}

	@Override
	@Transactional
	public void sendConfirmationMessages() throws ServiceException {
		try {
			List<Deal> deals = dealDao.getOnApprove();
			for (Deal deal : deals) {
				Message<Deal> message = MessagerFactory.getDealConfirmationMessageForCustomer();
				message.setDataObject(deal);
				message.send();
				
				message = MessagerFactory.getDealConfirmationMessageForSeller();
				message.setDataObject(deal);
				message.send();
			}
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
}
