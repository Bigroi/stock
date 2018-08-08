package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TenderService;

@Repository
public class TenderServiceImpl implements TenderService{
	
	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Tender getById(long id, long companyId) throws ServiceException {
		try{
			Tender tender;
			if (id == -1) {
				tender = new Tender();
				tender.setCompanyId(companyId);
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
	public List<Tender> getByCompanyId(long companyId) throws ServiceException {
		try{
			return tenderDao.getByCompanyId(companyId);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(long id, long companyId) throws ServiceException {
		try{
			tenderDao.delete(id, companyId);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void merge(Tender tender, long companyId) throws ServiceException {
		try {
			if (tender.getId() == -1){
				tender.setCompanyId(companyId);
				tenderDao.add(tender);
			} else {
				tenderDao.update(tender, companyId);
			}
			Product product = productDao.getById(tender.getProductId());
			tender.setProduct(product);
			
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void activate(long id, long companyId) throws ServiceException {
		try{
			tenderDao.setStatusById(id, companyId, BidStatus.ACTIVE);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void deactivate(long id, long companyId) throws ServiceException {
		try{
			tenderDao.setStatusById(id, companyId, BidStatus.INACTIVE);
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Tender> getBySessionId(String sessionId) throws ServiceException {
		try {
			return tenderDao.getByDescription(sessionId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteBySessionId(String sessionId) throws ServiceException {
		try {
			tenderDao.deleteByDescription(sessionId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
