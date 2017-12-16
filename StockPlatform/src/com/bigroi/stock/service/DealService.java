package com.bigroi.stock.service;

import com.bigroi.stock.bean.Deal;

public interface DealService {

	Deal getById(long id) throws ServiceException;

}
