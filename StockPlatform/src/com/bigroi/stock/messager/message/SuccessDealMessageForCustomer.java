package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class SuccessDealMessageForCustomer extends BaseMessage<Deal> {

	public SuccessDealMessageForCustomer(String fileName) throws MessageException {
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
		return super.getText().replaceAll("@id", getDataObject().getProductName() + "");
	}
}
