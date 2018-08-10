package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.CompanyAddress;

@Service
public interface AddressService {

	List<CompanyAddress> getCompanyAddresses(long companyId);

	CompanyAddress getAddressById(long id, long companyId);

	void merge(CompanyAddress address, long companyId);

	void delete(long id, long companyId);

	boolean hasAddress(CompanyAddress address, long companyId);
}
