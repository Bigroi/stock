package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Deal;

@Service
public interface DealService {

	Deal getById(long id, long companyId);

	List<Deal> getByUserId(long companyId);

	boolean reject(long id, long companyId);

	boolean approve(long id, long companyId);

	boolean transport(long id, long companyId);
	
    List<Deal> getListBySellerAndBuyerApproved();

}
