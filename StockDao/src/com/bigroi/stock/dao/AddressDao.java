package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.CompanyAddress;

public interface AddressDao {

	public List<CompanyAddress> getAddressesForCompany(long companyId) throws DaoException;
	
	public boolean updateAddress(CompanyAddress address) throws DaoException;
	
	public void addAddress(CompanyAddress address) throws DaoException;
		
	public boolean deleteAddress(long id, long companyId) throws DaoException;

	public CompanyAddress getAddressById(long id) throws DaoException;
}
