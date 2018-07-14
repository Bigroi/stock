package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.CompanyAddress;

public interface AddressDao {

	List<CompanyAddress> getAddressesForCompany(long companyId) throws DaoException;
	
	boolean updateAddress(CompanyAddress address) throws DaoException;
	
	void addAddress(CompanyAddress address) throws DaoException;
		
	boolean deleteAddress(long id, long companyId) throws DaoException;

	CompanyAddress getAddressById(long id, long companyId) throws DaoException;

	boolean hasAddress(CompanyAddress address, long companyId) throws DaoException;
}
