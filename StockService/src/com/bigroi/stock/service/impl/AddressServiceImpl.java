package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressDao addressDao;
	
	public List<CompanyAddress> getCompanyAddresses(long companyId) throws ServiceException{
		try{
			return addressDao.getAddressesForCompany(companyId);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public CompanyAddress getAddressById(long id) throws ServiceException {
		try {
			CompanyAddress address;
			if (id == -1) {
				address = new CompanyAddress();
				address.setId(id);
			} else {
				address = addressDao.getAddressById(id);
			}
			return address;
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void merge(CompanyAddress address, long companyId) throws ServiceException {
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

	@Override
	public void delete(long id, long companyId) throws ServiceException {
		try{
			addressDao.deleteAddress(id, companyId);
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
