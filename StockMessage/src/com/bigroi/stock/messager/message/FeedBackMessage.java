package com.bigroi.stock.messager.message;

import java.util.Locale;

import com.bigroi.stock.bean.ui.FeedBack;

public class FeedBackMessage extends BaseMessage<FeedBack>{

	private String managerEmaill;
	
	public FeedBackMessage(String managerEmaill){
		super();
		this.managerEmaill = managerEmaill;
	}
	@Override
	protected String getRecipient(FeedBack feedBack) {
		return managerEmaill;
	}
	
	@Override
	protected String getText(FeedBack feedBack, Locale locale) {
		return feedBack.getMessage() + 
				"/n email: " + feedBack.getEmail() +
				"/n language: " + locale;
	}
	
	@Override
	protected String getSubject(Locale locale) {
		return "message from user";
	}
	
}
