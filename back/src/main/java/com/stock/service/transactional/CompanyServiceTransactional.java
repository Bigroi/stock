package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.business.CompanyRecord;
import com.stock.service.CompanyService;

import java.util.List;
import java.util.UUID;

public class CompanyServiceTransactional implements CompanyService {

    private final Transactional transactional;
    private final CompanyService service;

    public CompanyServiceTransactional(Transactional transactional, CompanyService service) {
        this.transactional = transactional;
        this.service = service;
    }

    @Override
    public List<CompanyRecord> getCompanies() {
        return service.getCompanies();
    }

    @Override
    public boolean deactivate(UUID id) {
        return transactional.inTransaction(() -> service.deactivate(id));
    }

    @Override
    public boolean activate(UUID id) {
        return transactional.inTransaction(() -> service.activate(id));
    }
}
