package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;

public class DealConfirmationMessageForCustomer extends BaseMessage<Deal> {

	@Autowired
	private CompanyService companyService;
	
	public DealConfirmationMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			return companyService.getCompanyById(getDataObject().getBuyerAddress().getCompanyId()).getEmail();
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
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
