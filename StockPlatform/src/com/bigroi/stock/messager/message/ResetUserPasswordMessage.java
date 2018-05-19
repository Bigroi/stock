package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.db.StockUser;

public class ResetUserPasswordMessage extends BaseMessage<StockUser> {

	public ResetUserPasswordMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		return getDataObject().getUsername();
		
	}

	@Override
	protected String getText() throws MessageException {
		StockUser user = getDataObject();
		return super.getText()
				.replaceAll("@username", user.getUsername())
				.replaceAll("@password", user.getPassword());
	}
	
}
