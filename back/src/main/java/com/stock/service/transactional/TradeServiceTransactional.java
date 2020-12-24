package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.business.AddressRecord;
import com.stock.service.AddressService;
import com.stock.service.TradeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TradeServiceTransactional implements TradeService {

    private final Transactional transactional;
    private final TradeService service;

    public TradeServiceTransactional(Transactional transactional, TradeService service) {
        this.transactional = transactional;
        this.service = service;
    }


    @Override
    public void expirationCheck() {
        transactional.useTransaction(service::expirationCheck);
    }

    @Override
    public void cleanPreDeals() {
        transactional.useTransaction(service::cleanPreDeals);
    }

    @Override
    public void trade() {
        transactional.useTransaction(service::trade);
    }
}
