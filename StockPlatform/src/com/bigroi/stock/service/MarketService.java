package com.bigroi.stock.service;

public interface MarketService {

	void setExparation() throws ServiceException;
	
	void clearPreDeal() throws ServiceException;
	
	void trade() throws ServiceException;

	void sendConfimationMails() throws ServiceException;
}
