package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.ServiceException;

public class DealServiceImpl implements DealService{
	
	private BlacklistDao blacklistDao;
	private DealDao dealDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
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
	public Deal getById(long id, long companyId) throws ServiceException {
		try {
			return dealDao.getById(id, companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Deal> getByUserId(long companyId) throws ServiceException {
		try {
			return dealDao.getByCompanyId(companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public boolean reject(long id, long companyId) throws ServiceException {
		try{
			Deal deal = dealDao.getById(id, companyId);
			if (deal.getCustomerId() == companyId){
				deal.setCustomerApprovedBool(false);
				Message<Deal> message = MessagerFactory.getCustomerCanceledMessage();
				message.setDataObject(deal);
				message.send();
			} else if (deal.getSellerId() == companyId){
				deal.setSellerApprovedBool(false);
				Message<Deal> message = MessagerFactory.getSellerCanceledMessage();
				message.setDataObject(deal);
				message.send();
			} else {
				return false;
			}
			addBlackList(deal.getLotId(), deal.getTenderId());
			setVolumeBack(deal);
			dealDao.update(deal);
			return true;
		}catch(DaoException | MessageException e){
			throw new ServiceException(e);
		}
	}

	private void setVolumeBack(Deal deal) throws DaoException {
		Lot lot = lotDao.getById(deal.getLotId(), deal.getSellerId());
		lot.setMaxVolume(lot.getMaxVolume() + deal.getVolume());
		lotDao.update(lot, lot.getSellerId());
		
		Tender tender = tenderDao.getById(deal.getTenderId(), deal.getCustomerId());
		tender.setMaxVolume(tender.getMaxVolume() + deal.getVolume());
		tenderDao.update(tender, tender.getCustomerId());
	}

	@Override
	public boolean approve(long id, long companyId) throws ServiceException {
		try{
			Deal deal = dealDao.getById(id, companyId);
			if (deal.getCustomerId() == companyId){
				deal.setCustomerApprovedBool(true);
			} else if (deal.getSellerId() == companyId){
				deal.setSellerApprovedBool(true);
			} else {
				return false;
			}
			dealDao.update(deal);
			if (deal.getStatus() == DealStatus.APPROVED){
				Message<Deal> message = MessagerFactory.getSuccessDealMessageForCustomer();
				message.setDataObject(deal);
				message.send();
				
				message = MessagerFactory.getSuccessDealMessageForSeller();
				message.setDataObject(deal);
				message.send();
			}
			
			return true;
		}catch(DaoException | MessageException e){
			throw new ServiceException(e);
		}
	}

	private void addBlackList(long lotId, long tenderId) throws DaoException {
		Blacklist blackList = new Blacklist();
		blackList.setLotId(lotId);
		blackList.setTenderId(tenderId);
		blacklistDao.add(blackList);
	}

}
