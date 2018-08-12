package com.bigroi.stock.messager.message.deal;

import com.bigroi.stock.bean.db.Deal;

public class DealExparationMessageForSeller extends DealBaseMessage{

	public DealExparationMessageForSeller(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	protected long getCompanyId(Deal deal) {
		return deal.getSellerAddress().getCompanyId();																						
	}
	
	@Override
	protected String getText(Deal deal, String locale) {
		return super.getText(deal, locale)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@server", mailManager.getServerAdress());
	}
	
}
