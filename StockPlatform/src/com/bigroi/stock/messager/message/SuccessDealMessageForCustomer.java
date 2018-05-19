package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class SuccessDealMessageForCustomer extends BaseMessage<Deal> {

	public SuccessDealMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			return ServiceFactory.getCompanyService().getCompanyById(
					getDataObject().getBuyerAddress().getCompanyId()
					).getEmail();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
	
	@Override
	protected String getText() throws MessageException {
		return super.getText()
				.replaceAll("@product", getDataObject().getProduct().getName())
				.replaceAll("@server", MessagerFactory.getMailManager().getServerAdress());
	}
}
