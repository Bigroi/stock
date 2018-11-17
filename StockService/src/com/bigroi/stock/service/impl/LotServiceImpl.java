package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.LotService;

@Repository
public class LotServiceImpl implements LotService {

	protected static final int ANY_DISTANCE = 30000;
	@Autowired
	private LotDao lotDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public Lot getById(long id, long companyId){
		Lot lot;
		if (id == -1) {
			lot = new Lot();
			lot.setCompanyId(companyId);
			lot.setStatus(BidStatus.INACTIVE);
			lot.setDistance(ANY_DISTANCE);
			lot.setId(-1);
		} else {
			lot = lotDao.getById(id, companyId);
		}
		if (lot.getCategoryId() == null){
			lot.setCategoryId(-1l);
		}
		return lot;
	}

	@Override
	public void merge(Lot lot, long companyId){
		if (lot.getId() == -1){
			lot.setCompanyId(companyId);
			lotDao.add(lot);
		} else {
			lotDao.update(lot, companyId);
		}
		Product product = productDao.getById(lot.getProductId());
		lot.setProduct(product);
	}

	@Override
	public List<Lot> getByCompanyId(long companyId){
		return lotDao.getByCompanyId(companyId);
	}

	@Override
	public void activate(long id, long companyId){
		lotDao.setStatusById(id, companyId, BidStatus.ACTIVE);
	}

	@Override
	public void delete(long id, long companyId){
		lotDao.delete(id, companyId);
	}

	@Override
	public void deactivate(long id, long companyId){
		lotDao.setStatusById(id, companyId, BidStatus.INACTIVE);
	}

	@Override
	public List<Lot> getBySessionId(String sessionId){
		return lotDao.getByDescription(sessionId);
	}

	@Override
	public void deleteBySessionId(String sessionId){
		lotDao.deleteByDescription(sessionId);
	}

}
