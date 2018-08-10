package com.bigroi.stock.lostener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.TenderService;

@WebListener
public class StockSessionListener implements HttpSessionActivationListener {

	@Autowired
	private LotService lotService;
	@Autowired
	private TenderService tenderService;
	
	
    public void sessionDidActivate(HttpSessionEvent se)  { 
    	//do nothing on session creation
    }

    public void sessionWillPassivate(HttpSessionEvent se)  { 
    	lotService.deleteBySessionId(se.getSession().getId());
    	tenderService.deleteBySessionId(se.getSession().getId());
    }
	
}
