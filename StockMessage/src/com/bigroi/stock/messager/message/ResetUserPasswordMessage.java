package com.bigroi.stock.messager.message;

import java.util.Locale;

import com.bigroi.stock.bean.db.StockUser;

public class ResetUserPasswordMessage extends BaseMessage<StockUser> {

	public ResetUserPasswordMessage(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(StockUser stockUser) {
		return stockUser.getUsername();
		
	}

	@Override
	protected String getText(StockUser user, Locale locale) {
		return super.getText(user, locale)
				.replaceAll("@username", user.getUsername())
				.replaceAll("@password", user.getPassword());
	}
	
}
