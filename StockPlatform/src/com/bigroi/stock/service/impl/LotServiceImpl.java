package com.bigroi.stock.service.impl;

import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;
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
				lot.setStatus(Status.DRAFT);
				lot.setId(-1);
			} else {
				lot = lotDao.getById(id);
			}
			return lot;
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void merge(Lot lot) throws ServiceException {
		try {
			if (lot.getId() == -1){
				lotDao.add(lot);
			} else {
				lotDao.update(lot);
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
	public void startTrading(long id) throws ServiceException {
		try {
			lotDao.setStatusById(id, Status.ACTIVE);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void cancel(long id) throws ServiceException {
		try {
			lotDao.setStatusById(id, Status.CANCELED);
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

}
