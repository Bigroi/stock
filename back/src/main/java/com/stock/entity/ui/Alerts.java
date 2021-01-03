package com.stock.entity.ui;

public class Alerts {

    private final long lotAlerts;
    private final long tenderAlerts;
    private final long dealOnApprove;
    private final long canceledDeal;

    public Alerts(long lotAlerts, long tenderAlerts, long dealOnApprove, long canceledDeal) {
        this.lotAlerts = lotAlerts;
        this.tenderAlerts = tenderAlerts;
        this.dealOnApprove = dealOnApprove;
        this.canceledDeal = canceledDeal;
    }

    public long getLotAlerts() {
        return lotAlerts;
    }

    public long getTenderAlerts() {
        return tenderAlerts;
    }

    public long getDealOnApprove() {
        return dealOnApprove;
    }

    public long getCanceledDeal() {
        return canceledDeal;
    }
}
