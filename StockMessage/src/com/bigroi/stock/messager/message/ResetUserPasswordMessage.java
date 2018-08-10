package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.db.StockUser;

public class ResetUserPasswordMessage extends BaseMessage<StockUser> {

	public ResetUserPasswordMessage(String fileName) {
		super(fileName);
	}

	@Override
	protected String getRecipient(StockUser stockUser) {
		return stockUser.getUsername();
		
	}

	@Override
	protected String getText(StockUser user) {
		return super.getText(user)
				.replaceAll("@username", user.getUsername())
				.replaceAll("@password", user.getPassword());
	}
	
}
