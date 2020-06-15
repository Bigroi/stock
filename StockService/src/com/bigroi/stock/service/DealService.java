package com.bigroi.stock.service;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.db.Deal;

import java.util.List;

public interface DealService {

    Deal getById(long id, long companyId);

    List<Deal> getByUserId(long companyId);

    boolean reject(long id, long companyId);

    DealStatus approve(long id, long companyId);

    boolean transport(long id, long companyId);

    List<Deal> getListBySellerAndBuyerApproved();

}
