package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.Address;

public interface AddressService {

	List<Address> getCompanyAddresses(long companyId) throws ServiceException;

}
