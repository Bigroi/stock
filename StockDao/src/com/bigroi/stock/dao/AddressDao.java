package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Address;

public interface AddressDao {

	public List<Address> getAddressesForCompany(long companyId) throws DaoException;
	
	public boolean updateAddress(Address address) throws DaoException;
	
	public void addAddress(Address address) throws DaoException;
		
	public boolean deleteAddress(long id, long companyId) throws DaoException;

	public Address getAddressById(long id) throws DaoException;
}
