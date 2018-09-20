package com.bigroi.stock.messager.message.deal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.docs.DealDocument;
import com.bigroi.stock.messager.message.BaseMessage;

public class SuccessDealMessageForBuyer extends BaseMessage<Deal> {

	@Autowired
	private DealDocument dealDocument;
	
	public SuccessDealMessageForBuyer(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}
	
	@Override
	protected byte[] getFile(Deal deal) {
		return dealDocument.getDocument(deal);
	}
	
	@Override
	protected String getRecipient(Deal deal) {
		return deal.getBuyerEmail();
	}
	
	@Override
	protected String getFileName() {
		return DealDocument.DEAL_DOC_FILE_NAME + "." + DealDocument.DEAL_DOC_FILE_EXTENSION;
	}

	@Override
	protected String getText(Deal deal, String locale) {
		return super.getText(deal, locale)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
