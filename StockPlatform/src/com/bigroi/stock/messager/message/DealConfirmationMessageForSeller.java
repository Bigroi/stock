package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class DealConfirmationMessageForSeller extends BaseMessage<Deal> {

	public DealConfirmationMessageForSeller(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			return ServiceFactory.getCompanyService().getCompanyById(
					getDataObject().getSellerAddress().getCompanyId()
					).getEmail();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText() throws MessageException {
		Deal deal = getDataObject();
		return super.getText()
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@server", MessagerFactory.getMailManager().getServerAdress());
	}
}
