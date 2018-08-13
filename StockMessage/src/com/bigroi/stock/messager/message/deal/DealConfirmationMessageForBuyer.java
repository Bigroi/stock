package com.bigroi.stock.messager.message.deal;

import com.bigroi.stock.bean.db.Deal;

public class DealConfirmationMessageForBuyer extends DealBaseMessage {

	public DealConfirmationMessageForBuyer(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	protected long getCompanyId(Deal deal) {
		return deal.getBuyerAddress().getCompanyId();																						
	}

	@Override
	protected String getText(Deal deal, String locale) {
		return super.getText(deal, locale)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@server", mailManager.getServerAdress());
	}
}