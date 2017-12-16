package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealConfirmationMessageForCustomer extends BaseMessage<Deal> {

	public DealConfirmationMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			Tender tender = ServiceFactory.getTenderService().getTender(getDataObject().getTenderId(), 0);
			return ServiceFactory.getCompanyService().getCompanyById(tender.getCustomerId()).getAddress(); // TODO get email
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText() throws MessageException {
		try{
			Deal deal = getDataObject();
			Tender tender = ServiceFactory.getTenderService().getTender(deal.getTenderId(), 0);
			Lot lot = ServiceFactory.getLotService().getLot(deal.getLotId(), 0);
			String customerLink = MessagerFactory.getLink().getCustomerConfirmationLink() + "?id=" + deal.getId()
			//TODO hash code
			+ "&key=" + deal.getCustomerId() + "&action=";
			
			return super.getText().replaceAll("@tenderId", deal.getTenderId() + "")
					.replaceAll("@tenderDate", tender.getDateStr())
					.replaceAll("@tenderPrice", tender.getMaxPrice() + "")
					.replaceAll("@price", (lot.getMinPrice() + tender.getMaxPrice()) / 2 + "")
					.replaceAll("@customerLinkApprove", customerLink + Action.APPROVE) 
					.replaceAll("@customerLinkCancel", customerLink + Action.CANCEL);
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
}
