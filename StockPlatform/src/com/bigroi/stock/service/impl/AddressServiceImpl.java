package com.bigroi.stock.service.impl;

import java.util.List;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.ServiceException;

public class AddressServiceImpl implements AddressService{

	private AddressDao addressDao;
	
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
	public List<Address> getCompanyAddresses(long companyId) throws ServiceException{
		try{
			return addressDao.getAddressesForCompany(companyId);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Address getAddressById(long id) throws ServiceException {
		try{
			return addressDao.getAddressById(id);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void merge(Address address, long companyId) throws ServiceException {
		try{
			if(address.getId() == -1){
				address.setCompanyId(companyId);
				addressDao.addAddress(address);
			}else{
				address.setCompanyId(companyId);
				addressDao.updateAddress(address);
			}
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
