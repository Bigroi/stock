package com.bigroi.stock.bean.common;

public enum DealStatus {

	ON_APPROVE,
	ON_PARTNER_APPROVE,
	REJECTED,
	APPROVED,
	TRANSPORT;
	
	public static DealStatus calculateStatus(PartnerChoice choice1, PartnerChoice choice2){
		int code = choice1.getCode() | choice2.getCode();
		if ((code & 2) != 0){
			return REJECTED;
		} else if ((code & 3) == 1) {
			return ON_APPROVE;
		} else if ((code & 7) == 4) {
			return APPROVED;
		} else if ((code & 15) == 8) {
			return TRANSPORT;
		} else {
			throw new IllegalArgumentException("can not calculate status of " + choice1 + " and " + choice2);
		}
	}
	
	@Override
	public String toString() {
		return "label.deal." + name().toLowerCase();
	}
	
}
