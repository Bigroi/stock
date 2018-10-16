package com.bigroi.stock.messager.message;

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
	protected String getText(FeedBack feedBack, String locale) {
		return feedBack.getMessage() + 
				"/n email: " + feedBack.getEmail() +
				"/n language: " + locale;
	}
	
	@Override
	protected String getSubject(String locale) {
		return "message from user";
	}
	
}
