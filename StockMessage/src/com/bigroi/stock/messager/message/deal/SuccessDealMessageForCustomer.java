package com.bigroi.stock.messager.message.deal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.docs.DealDocument;

public class SuccessDealMessageForCustomer extends DealBaseMessage {

	@Autowired
	private DealDocument dealDocument;
	
	public SuccessDealMessageForCustomer(String fileName) {
		super(fileName);
	}
	
	@Override
	protected byte[] getFile(Deal deal) {
		return dealDocument.getDocument(deal);
	}
	
	protected long getCompanyId(Deal deal) {
		return deal.getBuyerAddress().getCompanyId();																						
	}
	
	@Override
	protected String getFileName() {
		return DealDocument.DEAL_DOC_FILE_ANME;
	}

	@Override
	protected String getText(Deal deal) {
		return super.getText(deal)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
