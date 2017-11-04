package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public class ResetUserPasswordMessage extends BaseMessage<User> {

	public ResetUserPasswordMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try {
			Company company = ServiceFactory
					.getCompanyService().getCompanyById(getDataObject().getCompanyId());
			return company.getEmail();
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
		
	}

	@Override
	protected String getText() throws MessageException {
		User user = getDataObject();
		return super.getText()
				.replaceAll("@login", user.getLogin())
				.replaceAll("@password", user.getPassword());
	}
	
}
