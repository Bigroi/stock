package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;

public interface LotDao {
	
	void add(Lot lot);
	
	boolean update(Lot lot, long companyId);
	
	Lot getById(long id, long companyId);
	
	List<Lot> getByCompanyId(long companyId);
	
	void update(Collection<Lot> lotsToUpdate);

	void delete(long id, long companyId);

	boolean setStatusById(long id, long companyId, BidStatus active);

	boolean setStatusByCompanyId(long companyId, BidStatus inactive);

	boolean setStatusByProductId(long id, BidStatus inactive);

	List<Lot> getActive();

	List<Lot> getActiveByProductId(long productId);
	
	void closeLots();

	List<Lot> getByDescription(String description);

	void deleteByDescription(String description);
}
