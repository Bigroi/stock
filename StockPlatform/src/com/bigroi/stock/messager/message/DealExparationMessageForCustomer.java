package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealExparationMessageForCustomer extends BaseMessage<Deal>{

	public DealExparationMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getText() throws MessageException {
		return super.getText()
				.replaceAll("@id", getDataObject().getTenderId() + "");
	}
	
	@Override
	protected String getEmail() throws MessageException {
		try{
			Tender tender = ServiceFactory.getTenderService()
					.getTender(getDataObject().getTenderId(), 0);
			return ServiceFactory.getCompanyService().getCompanyById(tender.getCustomerId()).getAddress();// TODO get email
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

}
