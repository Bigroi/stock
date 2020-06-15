package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.Deal;

import java.util.List;

public interface TradeService {

    TradeService newInstance();

    void trade();

    List<Deal> testTrade(String sessionId);

}
