package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealConfirmationMessageForCustomer extends BaseMessage<PreDeal> {

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
			PreDeal preDeal = getDataObject();
			Tender tender = ServiceFactory.getTenderService().getTender(preDeal.getTenderId(), 0);
			Lot lot = ServiceFactory.getLotService().getLot(preDeal.getLotId(), 0);
			String customerLink = MessagerFactory.getLink().getCustomerConfirmationLink() + "?id=" + preDeal.getId()
			+ "&key=" + preDeal.getCustomerHashCode() + "&action=";
			
			return super.getText().replaceAll("@tenderId", preDeal.getTenderId() + "")
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
