package com.bigroi.stock.dao;

import java.util.Collection;
import java.util.List;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.db.TradeTender;

public interface TenderDao {
	
    void add(Tender tender);
	
	boolean update(Tender tender, long companyId);
	
	Tender getById(long id, long companyId);
	
	List<Tender> getByCompanyId(long companyId);
	
	boolean setStatusByCompanyId(long companyId, BidStatus status);

	boolean setStatusByProductId(long productId, BidStatus status);
	
	void setStatusById(long id, long companyId, BidStatus status);

	void update(Collection<TradeTender> tenders);

	void delete(long id, long companyId);

	List<Tender> getActive();

	List<Tender> getActiveByProductId(long productId);
	
	void closeTeners();

	List<Tender> getByDescription(String decsciption);

	void deleteByDescription(String decsciption);

	void updateStatus(List<Tender> tenders);

}
