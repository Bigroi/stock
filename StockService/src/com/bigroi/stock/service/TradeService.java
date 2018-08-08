package com.bigroi.stock.service;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Deal;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface TradeService {
	
	void trade() throws ServiceException;

	List<Deal> testTrade(String sessionId) throws ServiceException;

}
