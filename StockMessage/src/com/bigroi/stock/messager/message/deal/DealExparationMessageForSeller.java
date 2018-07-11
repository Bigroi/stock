package com.bigroi.stock.messager.message.deal;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.messager.message.MessageException;

public class DealExparationMessageForSeller extends DealBaseMessage{

	public DealExparationMessageForSeller(String fileName) throws MessageException {
		super(fileName);
	}

	protected long getCompanyId(Deal deal) {
		return deal.getSellerAddress().getCompanyId();																						
	}
	
	@Override
	protected String getText(Deal deal) throws MessageException {
		return super.getText(deal)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@server", mailManager.getServerAdress());
	}
	
}
