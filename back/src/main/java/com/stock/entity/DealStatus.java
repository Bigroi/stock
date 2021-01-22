package com.stock.entity;

public enum DealStatus {

    ON_APPROVE,
    ON_PARTNER_APPROVE,
    REJECTED,
    APPROVED;

    public static DealStatus calculateStatus(PartnerChoice userChoice, PartnerChoice partnerChoice) {
        if (userChoice == PartnerChoice.REJECTED || partnerChoice == PartnerChoice.REJECTED) {
            return REJECTED;
        } else if (userChoice == PartnerChoice.ON_APPROVE) {
            return ON_APPROVE;
        } else if (partnerChoice == PartnerChoice.ON_APPROVE) {
            return ON_PARTNER_APPROVE;
        } else {
            return APPROVED;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
