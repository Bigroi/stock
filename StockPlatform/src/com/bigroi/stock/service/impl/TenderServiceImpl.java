package com.bigroi.stock.service.impl;

import java.util.List;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TenderService;

public class TenderServiceImpl implements TenderService{
	
	private TenderDao tenderDao;
	
	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	@Override
	public Tender getTender(long id, long companyId) throws ServiceException {
		try{
			Tender tender;
			if (id == -1) {
				tender = new Tender();
				tender.setCustomerId(companyId);
				tender.setStatus(BidStatus.INACTIVE);
				tender.setId(-1);
			} else {
				tender = tenderDao.getById(id);
			}
			return tender;
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Tender> getMyList(long companyId) throws ServiceException {
		try{
			return tenderDao.getByCustomerId(companyId);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteById(long id) throws ServiceException {
		try{
			tenderDao.deleteById(id);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void merge(Tender tender) throws ServiceException {
		try {
			if (tender.getId() == -1){
				tenderDao.add(tender);
			} else {
				tenderDao.update(tender);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void startTrading(long id) throws ServiceException {
		try{
			tenderDao.setStatusById(id, BidStatus.ACTIVE);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Tender> getByProduct(int productId) throws ServiceException {
		try{
			return tenderDao.getActiveByProductId(productId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void endTrading(long id) throws ServiceException {
		try{
			tenderDao.setStatusById(id, BidStatus.INACTIVE);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
		
	}
}
