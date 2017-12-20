package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealExparationMessageForSeller extends BaseMessage<Deal>{

	public DealExparationMessageForSeller(String fileName) throws MessageException {
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
			return ServiceFactory.getCompanyService().getCompanyById(getDataObject().getSellerId()).getAllEmails();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	
}
