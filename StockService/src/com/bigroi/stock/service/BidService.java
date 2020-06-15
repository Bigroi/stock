package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.Bid;

import java.util.List;

public interface BidService<T extends Bid> {

    T getById(long id, long companyId);

    List<T> getByCompanyId(long salerId);

    List<T> getBySessionId(String sessionId);

    void activate(long id, long companyId);

    void delete(long id, long companyId);

    void merge(T lot, long companyId);

    void deactivate(long id, long companyId);

    void deleteBySessionId(String sessionId);
}
