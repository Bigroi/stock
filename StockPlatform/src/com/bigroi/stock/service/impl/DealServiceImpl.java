package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealsDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.ServiceException;

public class DealServiceImpl implements DealService{
	
	private PreDealDao preDealDao;
	private BlacklistDao blacklistDao;
	private DealsDao dealsDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
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
	public PreDeal getById(long id) throws ServiceException {
		try {
			return preDealDao.getById(id);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Transactional
	private void addBlackList(long lotId, long tenderId) throws DaoException {
		Blacklist blackList = new Blacklist();
		blackList.setLotId(lotId);
		blackList.setTenderId(tenderId);
		blacklistDao.add(blackList);
	}
	
	@Override
	public void setApprovedBySeller(long preDealId) throws ServiceException{
		try{
			PreDeal preDeal = getById(preDealId);
			preDeal.setSellerApprovBool(true);
			preDealDao.update(preDeal);
			if (preDeal.getCustApprovBool()) {				
				finalizeDeal(preDeal);
			}
		}catch(DaoException  e){
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
	
	private void finalizeDeal(PreDeal preDeal) throws ServiceException{
		try{
			Deals deal = new Deals();
			deal.setLotId(preDeal.getLotId());
			deal.setTenderId(preDeal.getTenderId());
			dealsDao.add(deal);
			
			lotDao.setStatusById(preDeal.getLotId(), Status.SUCCESS);
			tenderDao.setStatusById(preDeal.getTenderId(), Status.SUCCESS);	
			
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
	public void setApprovedByCustomer(long preDealId) throws ServiceException{
		try{
			PreDeal preDeal = getById(preDealId);
			preDeal.setCustApprovBool(true);
			preDealDao.update(preDeal);
			if (preDeal.getSellerApprovBool()) {				
				finalizeDeal(preDeal);
			}
		}catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void cancel(long preDealId, boolean seller) throws ServiceException{
		try{
			PreDeal preDeal = getById(preDealId);
			addBlackList(preDeal.getLotId(), preDeal.getTenderId());
			lotDao.setStatusById(preDeal.getLotId(), Status.IN_GAME);
			tenderDao.setStatusById(preDeal.getTenderId(), Status.IN_GAME);
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
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(PreDeal preDeal) throws ServiceException {
		try{
			preDealDao.add(preDeal);
			
			lotDao.setStatusById(preDeal.getId(), Status.ON_APPROVAL);
			tenderDao.setStatusById(preDeal.getId(), Status.ON_APPROVAL);
			
			Message<PreDeal> message = MessagerFactory.getDealConfirmationMessageForCustomer();
			message.setDataObject(preDeal);
			message.send();
			
			message = MessagerFactory.getDealConfirmationMessageForSeller();
			message.setDataObject(preDeal);
			message.send();
		}catch (DaoException | MessageException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

}
