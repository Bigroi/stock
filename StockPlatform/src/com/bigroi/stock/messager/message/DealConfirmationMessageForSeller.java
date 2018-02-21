package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealConfirmationMessageForSeller extends BaseMessage<Deal> {

	public DealConfirmationMessageForSeller(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			return ServiceFactory.getCompanyService().getCompanyById(getDataObject().getSellerId()).getAllEmails();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText() throws MessageException {
		Deal deal = getDataObject();
		String sellerLink = "";
		return super.getText()
				.replaceAll("@lotId", deal.getProductName() + "")
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@sellerLinkApprove", sellerLink + Action.APPROVE)
				.replaceAll("@sellerLinkCancel", sellerLink + Action.CANCEL);
	}
}
