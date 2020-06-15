package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.db.TradeTender;
import com.bigroi.stock.dao.BidDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.TenderService;

import java.util.function.Supplier;

public class TenderServiceImpl extends BidBaseService<Tender, TradeTender> implements TenderService {

    private final TenderDao tenderDao;

    public TenderServiceImpl(int anyDistance, ProductDao productDao, TenderDao tenderDao) {
        super(anyDistance, productDao);
        this.tenderDao = tenderDao;
    }

    @Override
    protected Supplier<Tender> getConstructor() {
        return Tender::new;
    }

    @Override
    protected BidDao<Tender, TradeTender> getBidDao() {
        return tenderDao;
    }
}
