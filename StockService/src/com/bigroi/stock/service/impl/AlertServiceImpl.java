package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.ui.AlertForUI;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.AlertService;

public class AlertServiceImpl implements AlertService {

    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final DealDao dealDao;

    public AlertServiceImpl(LotDao lotDao, TenderDao tenderDao, DealDao dealDao) {
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.dealDao = dealDao;
    }

    @Override
    public AlertForUI getAlerts(long companyId) {
        var lotCount = lotDao.getByCompanyId(companyId).stream()
                .filter(Bid::isAlertBool)
                .count();
        var tenderCount = tenderDao.getByCompanyId(companyId).stream()
                .filter(Bid::isAlertBool)
                .count();
        var deals = dealDao.getByCompanyId(companyId);
        var dealOnApprove = deals.stream()
                .filter(d -> isOnApprove(d, companyId))
                .count();
        var canceledDeal = deals.stream()
                .filter(d -> d.getBuyerCompanyId() == companyId ? d.isBuyerAlertBool() : d.isSellerAlertBool())
                .count();
        return new AlertForUI(lotCount, tenderCount, dealOnApprove, canceledDeal);
    }

    @Override
    public void resetLotsAlerts(long companyId) {
        lotDao.resetAlerts(companyId);
    }

    @Override
    public void resetTenderAlerts(long companyId) {
        tenderDao.resetAlerts(companyId);
    }

    @Override
    public void resetDealAlerts(long companyId) {
        dealDao.resetAlerts(companyId);
    }

    private boolean isOnApprove(Deal deal, long companyId) {
        if (companyId == deal.getBuyerCompanyId()) {
            return deal.getBuyerChoice() == PartnerChoice.ON_APPROVE
                    && deal.getSellerChoice() != PartnerChoice.REJECTED;
        } else {
            return deal.getSellerChoice() == PartnerChoice.ON_APPROVE
                    && deal.getBuyerChoice() != PartnerChoice.REJECTED;
        }
    }
}
