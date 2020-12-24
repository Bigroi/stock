package com.stock.service;

import com.stock.entity.business.CompanyRecord;
import com.stock.entity.business.ProductCategoryRecord;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<CompanyRecord> getCompanies();

    boolean deactivate(UUID id);

    boolean activate(UUID id);
}
