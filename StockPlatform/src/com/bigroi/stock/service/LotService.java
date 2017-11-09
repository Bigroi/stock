package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Lot;

public interface LotService {
	
	Lot getLot (long id, long companyId) throws ServiceException;
	
	List<Lot> getBySellerId(long salerId) throws ServiceException;
	
	void startTrading(long id) throws ServiceException;
	
	void cancel(long id) throws ServiceException;
	
	void merge(Lot lot) throws ServiceException;

	List<Lot> getByProduct(int productId) throws ServiceException;
}
