package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.db.StockUser;

public class ResetUserPasswordMessage extends BaseMessage<StockUser> {

	public ResetUserPasswordMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail(StockUser stockUser) throws MessageException {
		return stockUser.getUsername();
		
	}

	@Override
	protected String getText(StockUser user) throws MessageException {
		return super.getText(user)
				.replaceAll("@username", user.getUsername())
				.replaceAll("@password", user.getPassword());
	}
	
}
