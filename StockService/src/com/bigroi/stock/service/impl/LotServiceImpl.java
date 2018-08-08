package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class LotServiceImpl implements LotService {

	@Autowired
	private LotDao lotDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public Lot getById(long id, long companyId) throws ServiceException {
		try{
			Lot lot;
			if (id == -1) {
				lot = new Lot();
				lot.setCompanyId(companyId);
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
				lot.setCompanyId(companyId);
				lotDao.add(lot);
			} else {
				lotDao.update(lot, companyId);
			}
			Product product = productDao.getById(lot.getProductId());
			lot.setProduct(product);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Lot> getByCompanyId(long companyId) throws ServiceException {
		try {
			return lotDao.getByCompanyId(companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void activate(long id, long companyId) throws ServiceException {
		try {
			lotDao.setStatusById(id, companyId, BidStatus.ACTIVE);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(long id, long companyId) throws ServiceException {
		try {
			lotDao.delete(id, companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deactivate(long id, long companyId) throws ServiceException {
		try {
			lotDao.setStatusById(id, companyId, BidStatus.INACTIVE);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<Lot> getBySessionId(String sessionId) throws ServiceException {
		try {
			return lotDao.getByDescription(sessionId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteBySessionId(String sessionId) throws ServiceException {
		try {
			lotDao.deleteByDescription(sessionId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
