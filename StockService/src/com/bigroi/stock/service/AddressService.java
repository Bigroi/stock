package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.CompanyAddress;

@Service
public interface AddressService {

	List<CompanyAddress> getCompanyAddresses(long companyId) throws ServiceException;

	CompanyAddress getAddressById(long id, long companyId) throws ServiceException;

	void merge(CompanyAddress address, long companyId) throws ServiceException;

	void delete(long id, long companyId) throws ServiceException;
}
