package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Deal;

@Service
public interface TradeService {
	
	void trade() throws ServiceException;

	List<Deal> testTrade(String sessionId) throws ServiceException;

}
