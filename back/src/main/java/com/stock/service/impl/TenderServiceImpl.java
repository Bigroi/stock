package com.stock.service.impl;

import com.stock.dao.LotDao;
import com.stock.dao.TenderDao;
import com.stock.entity.BidStatus;
import com.stock.entity.business.LotRecord;
import com.stock.entity.business.TenderRecord;
import com.stock.service.BidService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TenderServiceImpl implements BidService<TenderRecord> {

    private final TenderDao tenderDao;

    public TenderServiceImpl(TenderDao tenderDao) {
        this.tenderDao = tenderDao;
    }


    @Override
    public Optional<TenderRecord> getById(UUID id, UUID companyId) {
        return tenderDao.getByIdAndCompanyId(id, companyId);
    }

    @Override
    public List<TenderRecord> getByCompanyId(UUID companyId) {
        return tenderDao.getByCompanyId(companyId);
    }

    @Override
    public boolean delete(UUID id, UUID companyId) {
        return tenderDao.deleteByIdAndCompanyId(id, companyId);
    }

    @Override
    public Optional<UUID> add(TenderRecord tender, UUID companyId) {
        tender.setId(UUID.randomUUID());
        tender.setCompanyId(companyId);
        tender.setCreationDate(new Date());
        tender.setAlert(false);
        tenderDao.create(tender);
        return Optional.of(tender.getId());
    }

    @Override
    public boolean update(TenderRecord tender, UUID companyId) {
        tender.setCompanyId(companyId);
        return tenderDao.update(tender);
    }

    @Override
    public boolean deactivate(UUID id, UUID companyId) {
        return tenderDao.setStatus(id, companyId, BidStatus.INACTIVE);
    }

    @Override
    public boolean activate(UUID id, UUID companyId) {
        return tenderDao.setStatus(id, companyId, BidStatus.ACTIVE);
    }
}
