package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.TradeBid;

public interface BidDao<T extends Bid, E extends TradeBid> {

	void update(Collection<E> bids);
	
	void add(T bid);
	
	boolean update(T bid, long companyId);
	
	T getById(long id, long companyId);
	
	List<T> getByCompanyId(long companyId);
	
	void delete(long id, long companyId);

	boolean setStatusById(long id, long companyId, BidStatus active);

	boolean setStatusByCompanyId(long companyId, BidStatus inactive);

	boolean setStatusByProductId(long id, BidStatus inactive);

	List<T> getActive();

	List<T> getActiveByProductId(long productId);
	
	void close();

	List<T> getByDescription(String description);

	void deleteByDescription(String description);

	void updateStatus(List<T> lots);
	
}
