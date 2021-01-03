package com.stock.service.impl;

import com.stock.dao.DealDao;
import com.stock.dao.LotDao;
import com.stock.dao.TenderDao;
import com.stock.entity.PartnerChoice;
import com.stock.entity.ui.Alerts;
import com.stock.service.AlertsService;

import java.util.UUID;

public class AlertsServiceImpl implements AlertsService {

    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final DealDao dealDao;

    public AlertsServiceImpl(LotDao lotDao, TenderDao tenderDao, DealDao dealDao) {
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.dealDao = dealDao;
    }

    @Override
    public Alerts getAlerts(UUID companyId) {
        var lotCount = lotDao.getAlertCountForCompanyId(companyId);
        var tenderCount = tenderDao.getAlertCountForCompanyId(companyId);
        var deals = dealDao.getByCompanyId(companyId);
        var dealOnApprove = deals.stream()
                .filter(d -> d.getSellerCompanyId().equals(companyId)
                        ? d.getSellerChoice() == PartnerChoice.ON_APPROVE && d.getBuyerChoice() != PartnerChoice.REJECTED
                        : d.getBuyerChoice() == PartnerChoice.ON_APPROVE && d.getSellerChoice() != PartnerChoice.REJECTED)
                .count();
        var canceledDeal = deals.stream()
                .filter(d -> d.getSellerCompanyId().equals(companyId) ? d.isSellerAlert() : d.isBuyerAlert())
                .count();
        return new Alerts(lotCount, tenderCount, dealOnApprove, canceledDeal);
    }

    @Override
    public boolean resetLotsAlerts(UUID companyId) {
        return lotDao.resetAlertsForCompanyId(companyId);
    }

    @Override
    public boolean resetTenderAlerts(UUID companyId) {
        return tenderDao.resetAlertsForCompanyId(companyId);
    }

    @Override
    public boolean resetDealAlerts(UUID companyId) {
        dealDao.resetAlertsForSeller(companyId);
        dealDao.resetAlertsForBuyer(companyId);
        return true;
    }
}
