package com.stock.service;

import com.stock.entity.business.LotRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BidService<T> {

    Optional<T> getById(UUID id, UUID companyId);

    List<T> getByCompanyId(UUID companyId);

    boolean delete(UUID id, UUID companyId);

    Optional<UUID> add(T bid, UUID companyId);

    boolean update(T bid, UUID companyId);

    boolean deactivate(UUID id, UUID companyId);

    boolean activate(UUID id, UUID companyId);
}
