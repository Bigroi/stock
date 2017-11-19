package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class LotExparationMessage extends BaseMessage<Lot> {

	protected LotExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try {
			long companyId = getDataObject().getSellerId();
			return ServiceFactory.getCompanyService().getCompanyById(companyId).getEmail();
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
	
	@Override
	protected String getText() throws MessageException {
		return super.getText()
				.replaceAll("@price", getDataObject().getMinPrice() + "")
				.replaceAll("@description", getDataObject().getDescription())
				.replaceAll("@volume", getDataObject().getVolume() + "");
	}

}
