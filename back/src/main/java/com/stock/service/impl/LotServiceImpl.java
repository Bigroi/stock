package com.stock.service.impl;

import com.stock.dao.LotDao;
import com.stock.entity.BidStatus;
import com.stock.entity.business.LotRecord;
import com.stock.service.BidService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LotServiceImpl implements BidService<LotRecord> {

    private final LotDao lotDao;

    public LotServiceImpl(LotDao lotDao) {
        this.lotDao = lotDao;
    }


    @Override
    public Optional<LotRecord> getById(UUID id, UUID companyId) {
        return lotDao.getByIdAndCompanyId(id, companyId);
    }

    @Override
    public List<LotRecord> getByCompanyId(UUID companyId) {
        return lotDao.getByCompanyId(companyId);
    }

    @Override
    public boolean delete(UUID id, UUID companyId) {
        return lotDao.deleteByIdAndCompanyId(id, companyId);
    }

    @Override
    public Optional<UUID> add(LotRecord lot, UUID companyId) {
        lot.setId(UUID.randomUUID());
        lot.setCompanyId(companyId);
        lot.setCreationDate(new Date());
        lot.setAlert(false);
        lotDao.create(lot);
        return Optional.of(lot.getId());
    }

    @Override
    public boolean update(LotRecord lot, UUID companyId) {
        lot.setCompanyId(companyId);
        return lotDao.update(lot);
    }

    @Override
    public boolean deactivate(UUID id, UUID companyId) {
        return lotDao.setStatus(id, companyId, BidStatus.INACTIVE);
    }

    @Override
    public boolean activate(UUID id, UUID companyId) {
        return lotDao.setStatus(id, companyId, BidStatus.ACTIVE);
    }
}
