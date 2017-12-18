package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.Deal;

public interface DealService {

	Deal getById(long id, long companyId) throws ServiceException;

	List<Deal> getByUserId(long companyId) throws ServiceException;

	boolean reject(long id, long companyId) throws ServiceException;

	boolean approve(long id, long companyId) throws ServiceException;

}
