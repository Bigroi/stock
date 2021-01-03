package com.stock.service.impl;

import com.stock.dao.CompanyDao;
import com.stock.dao.LotDao;
import com.stock.dao.TenderDao;
import com.stock.entity.BidStatus;
import com.stock.entity.CompanyStatus;
import com.stock.entity.business.CompanyRecord;
import com.stock.service.CompanyService;

import java.util.List;
import java.util.UUID;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;

    public CompanyServiceImpl(
            CompanyDao companyDao,
            LotDao lotDao,
            TenderDao tenderDao
    ) {
        this.companyDao = companyDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
    }

    @Override
    public List<CompanyRecord> getCompanies() {
        return companyDao.getAllCompanies();
    }

    @Override
    public boolean deactivate(UUID id) {
        if (companyDao.changeCompanyStatus(id, CompanyStatus.REVOKED)) {
            lotDao.setStatusByCompanyId(id, BidStatus.INACTIVE);
            tenderDao.setStatusByCompanyId(id, BidStatus.INACTIVE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean activate(UUID id) {
        return companyDao.changeCompanyStatus(id, CompanyStatus.VERIFIED);
    }
}
