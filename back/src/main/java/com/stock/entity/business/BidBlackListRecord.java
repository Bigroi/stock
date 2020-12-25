package com.stock.entity.business;

import java.util.UUID;

public class BidBlackListRecord {

    private UUID lotId;
    private UUID tenderId;

    public UUID getLotId() {
        return lotId;
    }

    public void setLotId(UUID lotId) {
        this.lotId = lotId;
    }

    public UUID getTenderId() {
        return tenderId;
    }

    public void setTenderId(UUID tenderId) {
        this.tenderId = tenderId;
    }
}
