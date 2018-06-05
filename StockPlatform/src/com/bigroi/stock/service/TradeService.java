package com.bigroi.stock.service;

import org.springframework.stereotype.Service;

@Service
public interface TradeService {
	
	public void trade() throws ServiceException;

}
