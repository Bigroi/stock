package com.bigroi.stock.bean.ui;

public class AlertForUI {

    private final long lotCount;
    private final long tenderCount;
    private final long dealOnApprove;
    private final long canceledDeal;

    public AlertForUI(long lotCount, long tenderCount, long dealOnApprove, long canceledDeal) {
        this.lotCount = lotCount;
        this.tenderCount = tenderCount;
        this.dealOnApprove = dealOnApprove;
        this.canceledDeal = canceledDeal;
    }

    public long getLotCount() {
        return lotCount;
    }

    public long getTenderCount() {
        return tenderCount;
    }

    public long getDealOnApprove() {
        return dealOnApprove;
    }

    public long getCanceledDeal() {
        return canceledDeal;
    }
}
