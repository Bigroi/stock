package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealExparationMessageForCustomer extends BaseMessage<Deal>{

	public DealExparationMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getText() throws MessageException {
		return super.getText()
				.replaceAll("@product", getDataObject().getProductName())
				.replaceAll("@server", MessagerFactory.getMailManager().getServerAdress());
	}
	
	@Override
	protected String getEmail() throws MessageException {
		try{
			return ServiceFactory.getCompanyService().getCompanyById(getDataObject().getCustomerId()).getAllEmails();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

}
