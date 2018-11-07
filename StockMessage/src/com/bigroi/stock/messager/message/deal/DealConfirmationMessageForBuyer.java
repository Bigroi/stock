package com.bigroi.stock.messager.message.deal;

import java.util.Locale;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.messager.message.BaseMessage;

public class DealConfirmationMessageForBuyer extends BaseMessage<Deal> {

	public DealConfirmationMessageForBuyer(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(Deal deal) {
		return deal.getBuyerEmail();
	}

	@Override
	protected String getText(Deal deal, Locale locale) {
		return super.getText(deal, locale)
				.replaceAll("@product", labelDao.getLabel(deal.getProductName(), "name", locale))
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
