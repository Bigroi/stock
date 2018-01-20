package com.bigroi.stock.service.impl;

import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ServiceException;

public class LotServiceImpl implements LotService {

	private LotDao lotDao;

	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}

	@Override
	public Lot getLot(long id, long companyId) throws ServiceException {
		try{
			Lot lot;
			if (id == -1) {
				lot = new Lot();
				lot.setSellerId(companyId);
				lot.setStatus(BidStatus.INACTIVE);
				lot.setId(-1);
			} else {
				lot = lotDao.getById(id, companyId);
			}
			return lot;
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void merge(Lot lot, long companyId) throws ServiceException {
		try {
			if (lot.getId() == -1){
				lot.setSellerId(companyId);
				lotDao.add(lot);
			} else {
				lotDao.update(lot, companyId);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Lot> getBySellerId(long salerId) throws ServiceException {
		try {
			return lotDao.getBySellerId(salerId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void activate(List<Long> ids, long companyId) throws ServiceException {
		try {
			lotDao.setStatusById(ids, companyId, BidStatus.ACTIVE);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(List<Long> ids, long companyId) throws ServiceException {
		try {
			lotDao.delete(ids, companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Lot> getByProduct(int productId) throws ServiceException {
		try{
			return lotDao.getActiveByProductId(productId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deactivate(List<Long> ids, long companyId) throws ServiceException {
		try {
			lotDao.setStatusById(ids, companyId, BidStatus.INACTIVE);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

}
