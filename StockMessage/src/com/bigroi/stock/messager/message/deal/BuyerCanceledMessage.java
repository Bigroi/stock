package com.bigroi.stock.messager.message.deal;

import java.util.Locale;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.messager.message.BaseMessage;

public class BuyerCanceledMessage extends BaseMessage<Deal> {

	public BuyerCanceledMessage(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(Deal deal) {
		return deal.getSellerEmail();
	}
	
	@Override
	protected String getText(Deal deal, Locale locale) {
		return getTextTemplate(locale)
				.replaceAll("@product", labelDao.getLabel(deal.getProductName(), "name", locale))
				.replaceAll("@server", mailManager.getServerAdress());
	}

}
