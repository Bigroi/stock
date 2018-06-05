package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;

public class SuccessDealMessageForCustomer extends BaseMessage<Deal> {

	@Autowired
	private CompanyService companyService;
	
	public SuccessDealMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try{
			return companyService.getCompanyById(
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
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
