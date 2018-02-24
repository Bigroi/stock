package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealConfirmationMessageForCustomer extends BaseMessage<Deal> {

	public DealConfirmationMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			return ServiceFactory.getCompanyService().getCompanyById(getDataObject().getCustomerId()).getAllEmails();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText() throws MessageException {
		Deal deal = getDataObject();
		String customerLink = "";
		
		return super.getText().replaceAll("@tenderId", deal.getProductName() + "")
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@customerLinkApprove", customerLink + Action.APPROVE) 
				.replaceAll("@customerLinkCancel", customerLink + Action.CANCEL);
	}
}
