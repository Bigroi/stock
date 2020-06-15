package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.CompanyAddress;

import java.util.List;

public interface AddressService {

    List<CompanyAddress> getCompanyAddresses(long companyId);

    CompanyAddress getAddressById(long id, long companyId);

    void merge(CompanyAddress address, long companyId);

    void delete(long id, long companyId);

    boolean hasAddress(CompanyAddress address, long companyId);
}
