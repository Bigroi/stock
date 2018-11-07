package com.bigroi.stock.messager.message.deal;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.messager.message.BaseMessage;

public class DealConfirmationMessageForSeller extends BaseMessage<Deal> {

	public DealConfirmationMessageForSeller(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}
	
	@Override
	protected String getRecipient(Deal deal) {
		return deal.getSellerEmail();
	}

	@Override
	protected String getText(Deal deal, String locale) {
		return super.getText(deal, locale)
				.replaceAll("@product", deal.getProductName())
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
