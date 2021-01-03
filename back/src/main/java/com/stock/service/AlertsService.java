package com.stock.service;

import com.stock.entity.ui.Alerts;

import java.util.UUID;

public interface AlertsService {

    Alerts getAlerts(UUID companyId);

    boolean resetLotsAlerts(UUID companyId);

    boolean resetTenderAlerts(UUID companyId);

    boolean resetDealAlerts(UUID companyId);

}
