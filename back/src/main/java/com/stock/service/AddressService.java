package com.stock.service;

import com.stock.entity.business.AddressRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {

    List<AddressRecord> getCompanyAddresses(UUID companyId);

    boolean updateAddress(AddressRecord address, UUID companyId);

    boolean setPrimary(UUID addressId, UUID companyId);

    Optional<UUID> addAddress(AddressRecord address, UUID companyId);

    boolean deleteAddress(UUID addressId, UUID companyId);
}
