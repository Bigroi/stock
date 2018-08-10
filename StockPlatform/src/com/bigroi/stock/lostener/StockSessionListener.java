package com.bigroi.stock.lostener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.TenderService;

@WebListener
public class StockSessionListener implements HttpSessionListener {

	@Autowired
	private LotService lotService;
	@Autowired
	private TenderService tenderService;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//do nothing on session creation
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		lotService.deleteBySessionId(se.getSession().getId());
    	tenderService.deleteBySessionId(se.getSession().getId());
	}
	
}
