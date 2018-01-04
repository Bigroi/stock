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
		try{
			Deal deal = getDataObject();
			Lot lot = ServiceFactory.getLotService().getLot(deal.getLotId(), 0);
			String sellerLink = "";
//			MessagerFactory.getLink().getSellerConfirmationLink() + "?id=" + deal.getId() + "&key="
//					+ deal.getSellerId() + "&action=";
			return super.getText()
					.replaceAll("@lotId", deal.getLotId() + "")
					.replaceAll("@lotDate", Bid.FORMATTER.format(lot.getExpDate()))
					.replaceAll("@lotPrice", lot.getMinPrice() + "")
					.replaceAll("@price", deal.getPrice() + "")
					.replaceAll("@sellerLinkApprove", sellerLink + Action.APPROVE)
					.replaceAll("@sellerLinkCancel", sellerLink + Action.CANCEL);
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
}
