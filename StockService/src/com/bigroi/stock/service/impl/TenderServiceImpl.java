package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.TenderService;

@Repository
public class TenderServiceImpl implements TenderService{
	
	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Tender getById(long id, long companyId) {
		Tender tender;
		if (id == -1) {
			tender = new Tender();
			tender.setCompanyId(companyId);
			tender.setStatus(BidStatus.INACTIVE);
			tender.setId(-1);
		} else {
			tender = tenderDao.getById(id, companyId);
		}
		if (tender.getCategoryId() == null){
			tender.setCategoryId(-1l);
		}
		return tender;
	}

	@Override
	public List<Tender> getByCompanyId(long companyId) {
		return tenderDao.getByCompanyId(companyId);
	}

	@Override
	public void delete(long id, long companyId) {
		tenderDao.delete(id, companyId);
	}
	
	@Override
	public void merge(Tender tender, long companyId) {
		if (tender.getId() == -1){
			tender.setCompanyId(companyId);
			tenderDao.add(tender);
		} else {
			tenderDao.update(tender, companyId);
		}
		Product product = productDao.getById(tender.getProductId());
		tender.setProduct(product);
	}

	@Override
	public void activate(long id, long companyId) {
		tenderDao.setStatusById(id, companyId, BidStatus.ACTIVE);
	}
	
	@Override
	public void deactivate(long id, long companyId) {
		tenderDao.setStatusById(id, companyId, BidStatus.INACTIVE);
	}
	
	@Override
	public List<Tender> getBySessionId(String sessionId) {
		return tenderDao.getByDescription(sessionId);
	}

	@Override
	public void deleteBySessionId(String sessionId) {
		tenderDao.deleteByDescription(sessionId);
	}
}
