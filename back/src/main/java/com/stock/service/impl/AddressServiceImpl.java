package com.stock.service.impl;

import com.stock.dao.AddressDao;
import com.stock.entity.business.AddressRecord;
import com.stock.service.AddressService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public List<AddressRecord> getCompanyAddresses(UUID companyId) {
        return addressDao.getByCompanyId(companyId);
    }

    @Override
    public boolean updateAddress(AddressRecord address, UUID companyId) {
        address.setCompanyId(companyId);
        return addressDao.update(address);
    }

    @Override
    public boolean setPrimary(UUID addressId, UUID companyId) {
        return addressDao.setPrimary(addressId, companyId);
    }

    @Override
    public Optional<UUID> addAddress(AddressRecord address, UUID companyId) {
        address.setPrimaryAddress(false);
        address.setCompanyId(companyId);
        address.setId(UUID.randomUUID());
        addressDao.create(address);
        return Optional.of(address.getId());
    }

    @Override
    public boolean deleteAddress(UUID addressId, UUID companyId) {
        return addressDao.removeByIdAndCompanyId(addressId, companyId);
    }
}
