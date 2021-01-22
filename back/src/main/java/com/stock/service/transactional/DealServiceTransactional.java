package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.DealStatus;
import com.stock.entity.PartnerChoice;
import com.stock.entity.ui.Deal;
import com.stock.entity.ui.DealDetails;
import com.stock.entity.ui.UserComment;
import com.stock.service.DealService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DealServiceTransactional implements DealService {

    private final Transactional transactional;
    private final DealService service;

    public DealServiceTransactional(Transactional transactional, DealService service) {
        this.transactional = transactional;
        this.service = service;
    }

    @Override
    public List<Deal> getByCompanyId(UUID companyId) {
        return service.getByCompanyId(companyId);
    }

    @Override
    public Optional<DealDetails> getById(UUID id, UUID companyId) {
        return service.getById(id, companyId);
    }

    @Override
    public Optional<DealStatus> setState(UUID id, UUID companyId, PartnerChoice choice) {
        return transactional.inTransaction(() -> service.setState(id, companyId, choice));
    }

    @Override
    public boolean createComment(UUID dealId, UUID companyId, UserComment comment) {
        return service.createComment(dealId, companyId, comment);
    }

    @Override
    public boolean updateComment(UUID dealId, UUID companyId, UserComment comment) {
        return service.updateComment(dealId, companyId, comment);
    }

    @Override
    public Optional<UserComment> getComment(UUID dealId, UUID companyId) {
        return service.getComment(dealId, companyId);
    }
}
