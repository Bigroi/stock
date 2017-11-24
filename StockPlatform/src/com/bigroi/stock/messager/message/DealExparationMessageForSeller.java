package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealExparationMessageForSeller extends BaseMessage<PreDeal>{

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
			Lot lot = ServiceFactory.getLotService()
					.getLot(getDataObject().getLotId(), 0);
			return ServiceFactory.getCompanyService().getCompanyById(lot.getSellerId()).getEmail();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	
}