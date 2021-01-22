package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.service.BidService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BidServiceTransactional<T> implements BidService<T> {

    private final Transactional transactional;
    private final BidService<T> service;

    public BidServiceTransactional(Transactional transactional, BidService<T> service) {
        this.transactional = transactional;
        this.service = service;
    }

    @Override
    public Optional<T> getById(UUID id, UUID companyId) {
        return service.getById(id, companyId);
    }

    @Override
    public List<T> getByCompanyId(UUID companyId) {
        return service.getByCompanyId(companyId);
    }

    @Override
    public boolean delete(UUID id, UUID companyId) {
        return transactional.inTransaction(() -> service.delete(id, companyId));
    }

    @Override
    public Optional<UUID> add(T bid, UUID companyId) {
        return transactional.inTransaction(() -> service.add(bid, companyId));
    }

    @Override
    public boolean update(T bid, UUID companyId) {
        return transactional.inTransaction(() -> service.update(bid, companyId));
    }

    @Override
    public boolean deactivate(UUID id, UUID companyId) {
        return transactional.inTransaction(() -> service.deactivate(id, companyId));
    }

    @Override
    public boolean activate(UUID id, UUID companyId) {
        return transactional.inTransaction(() -> service.activate(id, companyId));
    }
}
