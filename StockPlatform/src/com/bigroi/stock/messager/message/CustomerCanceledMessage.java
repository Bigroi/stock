package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class CustomerCanceledMessage extends BaseMessage<Deal> {

	public CustomerCanceledMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try {
			Lot lot = ServiceFactory.getLotService().getLot(getDataObject().getLotId(), 0);
			return ServiceFactory.getCompanyService().getCompanyById(lot.getSellerId()).getAddress();// TODO: get Email																						
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText() throws MessageException {
		return super.getText().replaceAll("@id", getDataObject().getLotId() + "");
	}

}
