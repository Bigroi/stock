package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Deal;

@Service
public interface DealService {

	Deal getById(long id, long companyId) throws ServiceException;

	List<Deal> getByUserId(long companyId) throws ServiceException;

	boolean reject(long id, long companyId) throws ServiceException;

	boolean approve(long id, long companyId) throws ServiceException;

	boolean transport(long id, long companyId) throws ServiceException;
	
    List<Deal> getListBySellerAndBuyerApproved() throws ServiceException;

}
