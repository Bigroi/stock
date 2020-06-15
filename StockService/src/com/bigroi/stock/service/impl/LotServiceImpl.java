package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.TradeLot;
import com.bigroi.stock.dao.BidDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.LotService;

import java.util.function.Supplier;

public class LotServiceImpl extends BidBaseService<Lot, TradeLot> implements LotService {

    private final LotDao lotDao;

    public LotServiceImpl(int anyDistance, ProductDao productDao, LotDao lotDao) {
        super(anyDistance, productDao);
        this.lotDao = lotDao;
    }

    @Override
    protected Supplier<Lot> getConstructor() {
        return Lot::new;
    }

    @Override
    protected BidDao<Lot, TradeLot> getBidDao() {
        return lotDao;
    }

}
