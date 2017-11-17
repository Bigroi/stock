package com.bigroi.stock.service;

public interface MarketService {

	void checkExparations() throws ServiceException;
	
	void clearPreDeal() throws ServiceException;

	void sendConfirmationMessages() throws ServiceException;
	
}
