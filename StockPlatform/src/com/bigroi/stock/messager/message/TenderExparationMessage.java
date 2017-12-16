package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class TenderExparationMessage extends BaseMessage<Tender> {

	public TenderExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try {
			long companyId = getDataObject().getCustomerId();
			return ServiceFactory.getCompanyService().getCompanyById(companyId).getAddress();// TODO get email
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
	
	@Override
	protected String getText() throws MessageException {
		return super.getText()
				.replaceAll("@price", getDataObject().getMaxPrice() + "")
				.replaceAll("@description", getDataObject().getDescription())
				.replaceAll("@volume", getDataObject().getMaxVolume() + "");
	}

}
