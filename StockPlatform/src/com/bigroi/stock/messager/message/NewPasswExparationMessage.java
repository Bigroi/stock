package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.StockUser;

public class NewPasswExparationMessage extends BaseMessage<StockUser> {

	

	protected NewPasswExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		return getDataObject().getUsername();
	}
	
	protected String getText() throws MessageException {
		StockUser user = getDataObject();
		return super.getText()
				.replaceAll("@password", user.getPassword())
		        .replaceAll("@username", user.getUsername());
	}

}
