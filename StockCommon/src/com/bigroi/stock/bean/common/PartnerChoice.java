package com.bigroi.stock.bean.common;

public enum PartnerChoice {

    ON_APPROVE(1),
    REJECTED(2),
    APPROVED(4),
    TRANSPORT(8);

    private int code;

    PartnerChoice(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PartnerChoice valueOf(int code) {
        switch (code) {
            case 1:
                return ON_APPROVE;
            case 2:
                return REJECTED;
            case 4:
                return APPROVED;
            case 8:
                return TRANSPORT;
            default:
                return null;
        }
    }
}
