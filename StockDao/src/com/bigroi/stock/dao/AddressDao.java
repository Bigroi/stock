package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.CompanyAddress;

public interface AddressDao {

	List<CompanyAddress> getAddressesForCompany(long companyId);
	
	boolean updateAddress(CompanyAddress address);
	
	void addAddress(CompanyAddress address);
		
	boolean deleteAddress(long id, long companyId);

	CompanyAddress getAddressById(long id, long companyId);

	boolean hasAddress(CompanyAddress address, long companyId);
}
