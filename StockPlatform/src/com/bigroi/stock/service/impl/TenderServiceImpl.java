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
				tender = tenderDao.getById(id, companyId);
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
	public void delete(List<Long> ids, long companyId) throws ServiceException {
		try{
			tenderDao.delete(ids, companyId);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void merge(Tender tender, long companyId) throws ServiceException {
		try {
			if (tender.getId() == -1){
				tender.setCustomerId(companyId);
				tenderDao.add(tender);
			} else {
				tenderDao.update(tender, companyId);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void activate(List<Long> ids, long companyId) throws ServiceException {
		try{
			tenderDao.setStatusById(ids, companyId, BidStatus.ACTIVE);
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
	public void deactivate(List<Long> ids, long companyId) throws ServiceException {
		try{
			tenderDao.setStatusById(ids, companyId, BidStatus.INACTIVE);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
		
	}
}
