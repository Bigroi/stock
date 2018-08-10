package com.bigroi.stock.service;

import org.springframework.stereotype.Service;

@Service
public interface MarketService {

	void checkExparations();
	
	void clearPreDeal();

	void sendConfirmationMessages();
	
}
