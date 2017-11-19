package com.bigroi.stock.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.ServiceException;

public class DealServiceImpl implements DealService{
	
	private PreDealDao preDealDao;
	private BlacklistDao blacklistDao;
	private DealDao dealDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
	public void setPreDealDao(PreDealDao preDealDao) {
		this.preDealDao = preDealDao;
	}
	
	public void setBlacklistDao(BlacklistDao blacklistDao) {
		this.blacklistDao = blacklistDao;
	}
	
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
	public PreDeal getById(long id) throws ServiceException {
		try {
			return preDealDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	private void addBlackList(long lotId, long tenderId) throws DaoException {
		Blacklist blackList = new Blacklist();
		blackList.setLotId(lotId);
		blackList.setTenderId(tenderId);
		blacklistDao.add(blackList);
	}
	
	@Override
	@Transactional
	public void setApprovedBySeller(long preDealId) throws ServiceException{
		try{
			PreDeal preDeal = getById(preDealId);
			if (preDeal.getCustApprovBool()) {				
				finalizeDeal(preDeal);
			} else {
				preDeal.setSellerApprovBool(true);
				preDealDao.update(preDeal);
			}
		}catch(DaoException  e){
			throw new ServiceException(e);
		}
	}
	
	private void finalizeDeal(PreDeal preDeal) throws ServiceException{
		try{
			Lot lot = lotDao.getById(preDeal.getLotId());
			lot.setStatus(Status.SUCCESS);
			lot.setVolume(preDeal.getVolume());
			lotDao.add(lot);
			
			Tender tender = tenderDao.getById(preDeal.getTenderId());
			tender.setStatus(Status.SUCCESS);
			tender.setVolume(preDeal.getVolume());
			tenderDao.add(tender);
			
			Deal deal = new Deal();
			deal.setLotId(lot.getId());
			deal.setTenderId(tender.getId());
			dealDao.add(deal);
			
			Message<PreDeal> message = MessagerFactory.getSuccessDealMessageForCustomer();
			message.setDataObject(preDeal);
			message.send();
			
			message = MessagerFactory.getSuccessDealMessageForSeller();
			message.setDataObject(preDeal);
			message.send();

			preDealDao.deletedById(preDeal.getId());
		}catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public void setApprovedByCustomer(long preDealId) throws ServiceException{
		try{
			PreDeal preDeal = getById(preDealId);
			preDeal.setCustApprovBool(true);
			preDealDao.update(preDeal);
			if (preDeal.getSellerApprovBool()) {				
				finalizeDeal(preDeal);
			}
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public void cancel(long preDealId, boolean seller) throws ServiceException{
		try{
			PreDeal preDeal = getById(preDealId);
			
			Lot lot = lotDao.getById(preDeal.getLotId());
			lot.setVolume(lot.getVolume() + preDeal.getVolume());
			lotDao.update(lot);
			
			Tender tender = tenderDao.getById(preDeal.getTenderId());
			tender.setVolume(tender.getVolume() + preDeal.getVolume());
			tenderDao.update(tender);
			
			addBlackList(preDeal.getLotId(), preDeal.getTenderId());
			
			preDealDao.deletedById(preDeal.getId());
			
			Message<PreDeal> message;
			if (seller){
				message = MessagerFactory.getSellerCanceledMessage();
			} else {
				message = MessagerFactory.getCustomerCanceledMessage();
			}
			message.setDataObject(preDeal);
			message.send();
		}catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}

}
