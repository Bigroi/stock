package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.Lot;

public interface LotService {
	
	Lot getLot (long id, long companyId) throws ServiceException;
	
	List<Lot> getByCompanyId(long salerId) throws ServiceException;
	
	void activate(long id, long companyId) throws ServiceException;
	
	void delete(long id, long companyId) throws ServiceException;
	
	void merge(Lot lot, long companyId) throws ServiceException;

	void deactivate(long id, long companyId) throws ServiceException;
}
