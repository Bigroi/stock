package com.bigroi.stock.service;

import org.springframework.stereotype.Service;

@Service
public interface MarketService {

	void checkExparations() throws ServiceException;
	
	void clearPreDeal() throws ServiceException;

	void sendConfirmationMessages() throws ServiceException;
	
}
