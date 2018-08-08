package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BidService<T> {
	
	T getById (long id, long companyId) throws ServiceException;
	
	List<T> getByCompanyId(long salerId) throws ServiceException;
	
	List<T> getBySessionId(String sessionId) throws ServiceException;
	
	void activate(long id, long companyId) throws ServiceException;
	
	void delete(long id, long companyId) throws ServiceException;
	
	void merge(T lot, long companyId) throws ServiceException;

	void deactivate(long id, long companyId) throws ServiceException;
	
	void deleteBySessionId(String sessionId) throws ServiceException;
}
