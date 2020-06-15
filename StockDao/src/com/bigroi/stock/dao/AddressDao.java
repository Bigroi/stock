package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.CompanyAddress;

import java.util.List;

public interface AddressDao {

    List<CompanyAddress> getAddressesForCompany(long companyId);

    boolean updateAddress(CompanyAddress address);

    void addAddress(CompanyAddress address);

    boolean deleteAddress(long id, long companyId);

    CompanyAddress getAddressById(long id, long companyId);

    boolean hasAddress(CompanyAddress address, long companyId);
}
