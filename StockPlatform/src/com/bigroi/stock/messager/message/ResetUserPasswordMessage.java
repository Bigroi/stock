package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class ResetUserPasswordMessage extends BaseMessage<StockUser> {

	public ResetUserPasswordMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try {
			Company company = ServiceFactory
					.getCompanyService().getCompanyById(getDataObject().getCompanyId());
			return company.getAllEmails();
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
		
	}

	@Override
	protected String getText() throws MessageException {
		StockUser user = getDataObject();
		return super.getText()
				.replaceAll("@username", user.getUsername())
				.replaceAll("@password", user.getPassword());
	}
	
}
