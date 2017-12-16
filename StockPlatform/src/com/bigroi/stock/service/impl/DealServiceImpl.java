package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
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
	public Deal getById(long id) throws ServiceException {
		try {
			return dealDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

//	private void addBlackList(long lotId, long tenderId) throws DaoException {
//		Blacklist blackList = new Blacklist();
//		blackList.setLotId(lotId);
//		blackList.setTenderId(tenderId);
//		blacklistDao.add(blackList);
//	}
	
//	@Override
//	@Transactional
//	public void setApprovedBySeller(long preDealId) throws ServiceException{
//		try{
//			PreDeal preDeal = getById(preDealId);
//			if (preDeal.getCustApprovBool()) {				
//				finalizeDeal(preDeal);
//			} else {
//				preDeal.setSellerApprovBool(true);
//				preDealDao.update(preDeal);
//			}
//		}catch(DaoException  e){
//			throw new ServiceException(e);
//		}
//	}
	
//	@Override
//	@Transactional
//	public void setApprovedByCustomer(long preDealId) throws ServiceException{
//		try{
//			PreDeal preDeal = getById(preDealId);
//			preDeal.setCustApprovBool(true);
//			preDealDao.update(preDeal);
//			if (preDeal.getSellerApprovBool()) {				
//				finalizeDeal(preDeal);
//			}
//		}catch (DaoException e) {
//			throw new ServiceException(e);
//		}
//	}

}
