package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealConfirmationMessageForSeller extends BaseMessage<PreDeal> {

	public DealConfirmationMessageForSeller(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			Lot lot = ServiceFactory.getLotService().getLot(getDataObject().getLotId(), 0);
			return ServiceFactory.getCompanyService().getCompanyById(lot.getSellerId()).getAddress();// TODO get email
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText() throws MessageException {
		try{
			PreDeal preDeal = getDataObject();
			Tender tender = ServiceFactory.getTenderService().getTender(preDeal.getTenderId(), 0);
			Lot lot = ServiceFactory.getLotService().getLot(preDeal.getLotId(), 0);
			String sellerLink = MessagerFactory.getLink().getSellerConfirmationLink() + "?id=" + preDeal.getId() + "&key="
					+ preDeal.getSellerHashCode() + "&action=";
			return super.getText()
					.replaceAll("@lotId", preDeal.getLotId() + "")
					.replaceAll("@lotDate", Bid.FORMATTER.format(lot.getExpDate()))
					.replaceAll("@lotPrice", lot.getMinPrice() + "")
					.replaceAll("@price", (lot.getMinPrice() + tender.getMaxPrice()) / 2 + "")
					.replaceAll("@sellerLinkApprove", sellerLink + Action.APPROVE)
					.replaceAll("@sellerLinkCancel", sellerLink + Action.CANCEL);
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
}
