package com.bigroi.stock.service;

public interface MarketService {

    void checkExpirations();

    void clearPreDeal();

    void sendConfirmationMessages();

}
