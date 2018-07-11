package com.bigroi.stock.messager.message.deal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.docs.DealDocument;
import com.bigroi.stock.docs.DocumentException;
import com.bigroi.stock.messager.message.MessageException;

public class SuccessDealMessageForSeller extends DealBaseMessage{

	@Autowired
	private DealDocument dealDocument;
	
	public SuccessDealMessageForSeller(String fileName) throws MessageException {
		super(fileName);
	}
	
	@Override
	protected byte[] getFile(Deal deal) throws MessageException {
		try {
			return dealDocument.getDocument(deal);
		} catch (DocumentException e) {
			throw new MessageException(e);
		}
	}
	
	protected long getCompanyId(Deal deal) {
		return deal.getSellerAddress().getCompanyId();																						
	}
	
	@Override
	protected String getFileName() {
		return DealDocument.DEAL_DOC_FILE_ANME;
	}

	@Override
	protected String getText(Deal deal) throws MessageException {
		return super.getText(deal)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@server", mailManager.getServerAdress());
	}
	
}
