package com.bigroi.stock.service.impl;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.TradeBid;
import com.bigroi.stock.dao.BidDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.BidService;

@Repository
public abstract class BidBaseService<T extends Bid, E extends TradeBid> implements BidService<T> {

	protected static final int ANY_DISTANCE = 30000;
	@Autowired
	private ProductDao productDao;

	protected abstract Supplier<T> getConstructor();
	protected abstract BidDao<T, E> getBidDao();
	
	@Override
	public T getById(long id, long companyId){
		T bid;
		if (id == -1) {
			bid = getConstructor().get();
			bid.setCompanyId(companyId);
			bid.setStatus(BidStatus.INACTIVE);
			bid.setDistance(ANY_DISTANCE);
			bid.setId(-1);
		} else {
			bid = getBidDao().getById(id, companyId);
		}
		if (bid.getCategoryId() == null){
			bid.setCategoryId(-1l);
		}
		return bid;
	}

	@Override
	public void merge(T bid, long companyId){
		if (bid.getId() == -1){
			bid.setCompanyId(companyId);
			getBidDao().add(bid);
		} else {
			getBidDao().update(bid, companyId);
		}
		Product product = productDao.getById(bid.getProductId());
		bid.setProduct(product);
	}

	@Override
	public List<T> getByCompanyId(long companyId){
		return getBidDao().getByCompanyId(companyId);
	}

	@Override
	public void activate(long id, long companyId){
		getBidDao().setStatusById(id, companyId, BidStatus.ACTIVE);
	}

	@Override
	public void delete(long id, long companyId){
		getBidDao().delete(id, companyId);
	}

	@Override
	public void deactivate(long id, long companyId){
		getBidDao().setStatusById(id, companyId, BidStatus.INACTIVE);
	}

	@Override
	public List<T> getBySessionId(String sessionId){
		return getBidDao().getByDescription(sessionId);
	}

	@Override
	public void deleteBySessionId(String sessionId){
		getBidDao().deleteByDescription(sessionId);
	}

}
