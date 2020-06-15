package com.bigroi.stock.listener;

import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.TenderService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class StockSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //do nothing on session creation
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        WebApplicationContextUtils
                .getRequiredWebApplicationContext(se.getSession().getServletContext())
                .getBean(LotService.class)
                .deleteBySessionId(se.getSession().getId());

        WebApplicationContextUtils
                .getRequiredWebApplicationContext(se.getSession().getServletContext())
                .getBean(TenderService.class)
                .deleteBySessionId(se.getSession().getId());
    }

}
