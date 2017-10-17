package com.bigroi.stock.service;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.common.Action;

public interface ReferenceService {

	String callSellerCheck(long id,String key,Action action) throws ServiceException;
	
	PreDeal getByIdPreDeal(long id) throws ServiceException;
	
	String callCustomerCheck(long id,String key,Action action) throws ServiceException;

}
