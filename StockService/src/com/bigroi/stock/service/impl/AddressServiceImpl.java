package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public List<CompanyAddress> getCompanyAddresses(long companyId) {
        return addressDao.getAddressesForCompany(companyId);
    }

    @Override
    public CompanyAddress getAddressById(long id, long companyId) {
        CompanyAddress address;
        if (id == -1) {
            address = new CompanyAddress();
            address.setLatitude(-1);
            address.setLongitude(-1);
            address.setId(id);
        } else {
            address = addressDao.getAddressById(id, companyId);
        }
        return address;
    }

    @Override
    public void merge(CompanyAddress address, long companyId) {
        if (address.getId() == -1) {
            address.setCompanyId(companyId);
            addressDao.addAddress(address);
        } else {
            address.setCompanyId(companyId);
            addressDao.updateAddress(address);
        }
    }

    @Override
    public void delete(long id, long companyId) {
        addressDao.deleteAddress(id, companyId);
    }

    @Override
    public boolean hasAddress(CompanyAddress address, long companyId) {
        return addressDao.hasAddress(address, companyId);
    }
}
