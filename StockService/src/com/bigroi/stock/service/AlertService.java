package com.bigroi.stock.service;

import com.bigroi.stock.bean.ui.AlertForUI;

public interface AlertService {

    AlertForUI getAlerts(long companyId);

    void resetLotsAlerts(long companyId);

    void resetTenderAlerts(long companyId);

    void resetDealAlerts(long companyId);
}
