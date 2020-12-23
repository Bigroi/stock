package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.business.AddressRecord;
import com.stock.entity.business.UserRecord;
import com.stock.entity.ui.AccountData;
import com.stock.entity.ui.LoginResponse;
import com.stock.entity.ui.RegistrationRequest;
import com.stock.service.AddressService;
import com.stock.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddressServiceTransactional implements AddressService {

    private final Transactional transactional;
    private final AddressService service;

    public AddressServiceTransactional(Transactional transactional, AddressService service) {
        this.transactional = transactional;
        this.service = service;
    }

    @Override
    public List<AddressRecord> getCompanyAddresses(UUID companyId) {
        return service.getCompanyAddresses(companyId);
    }

    @Override
    public boolean updateAddress(AddressRecord address, UUID companyId) {
        return transactional.inTransaction(() -> service.updateAddress(address, companyId));
    }

    @Override
    public boolean setPrimary(UUID addressId, UUID companyId) {
        return transactional.inTransaction(() -> service.setPrimary(addressId, companyId));
    }

    @Override
    public Optional<UUID> addAddress(AddressRecord address, UUID companyId) {
        return transactional.inTransaction(() -> service.addAddress(address, companyId));
    }

    @Override
    public boolean deleteAddress(UUID addressId, UUID companyId) {
        return transactional.inTransaction(() -> service.deleteAddress(addressId, companyId));
    }
}
