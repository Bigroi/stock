package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Address;

@Service
public interface AddressService {

	List<Address> getCompanyAddresses(long companyId) throws ServiceException;

	Address getAddressById(long id) throws ServiceException;

	void merge(Address address, long companyId) throws ServiceException;

	void delete(long id, long companyId) throws ServiceException;
}
