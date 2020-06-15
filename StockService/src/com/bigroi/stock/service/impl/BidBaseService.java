package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.TradeBid;
import com.bigroi.stock.dao.BidDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.BidService;

import java.util.List;
import java.util.function.Supplier;

public abstract class BidBaseService<T extends Bid, E extends TradeBid> implements BidService<T> {

    protected final int anyDistance;
    private final ProductDao productDao;

    public BidBaseService(int anyDistance, ProductDao productDao) {
        this.anyDistance = anyDistance;
        this.productDao = productDao;
    }

    protected abstract Supplier<T> getConstructor();

    protected abstract BidDao<T, E> getBidDao();

    @Override
    public T getById(long id, long companyId) {
        T bid;
        if (id == -1) {
            bid = getConstructor().get();
            bid.setCompanyId(companyId);
            bid.setStatus(BidStatus.INACTIVE);
            bid.setDistance(anyDistance);
            bid.setId(-1);
        } else {
            bid = getBidDao().getById(id, companyId);
        }
        if (bid.getCategoryId() == null) {
            bid.setCategoryId(-1l);
        }
        return bid;
    }

    @Override
    public void merge(T bid, long companyId) {
        if (bid.getId() == -1) {
            bid.setCompanyId(companyId);
            getBidDao().add(bid);
        } else {
            getBidDao().update(bid, companyId);
        }
        Product product = productDao.getById(bid.getProductId());
        bid.setProduct(product);
    }

    @Override
    public List<T> getByCompanyId(long companyId) {
        return getBidDao().getByCompanyId(companyId);
    }

    @Override
    public void activate(long id, long companyId) {
        getBidDao().setStatusById(id, companyId, BidStatus.ACTIVE);
    }

    @Override
    public void delete(long id, long companyId) {
        getBidDao().delete(id, companyId);
    }

    @Override
    public void deactivate(long id, long companyId) {
        getBidDao().setStatusById(id, companyId, BidStatus.INACTIVE);
    }

    @Override
    public List<T> getBySessionId(String sessionId) {
        return getBidDao().getByDescription(sessionId);
    }

    @Override
    public void deleteBySessionId(String sessionId) {
        getBidDao().deleteByDescription(sessionId);
    }

}
