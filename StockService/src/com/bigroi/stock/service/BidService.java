package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BidService<T> {
	
	T getById (long id, long companyId);
	
	List<T> getByCompanyId(long salerId);
	
	List<T> getBySessionId(String sessionId);
	
	void activate(long id, long companyId);
	
	void delete(long id, long companyId);
	
	void merge(T lot, long companyId);

	void deactivate(long id, long companyId);
	
	void deleteBySessionId(String sessionId);
}
