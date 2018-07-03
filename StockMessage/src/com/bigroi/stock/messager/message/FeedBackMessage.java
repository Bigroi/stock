package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.ui.FeedBack;

public class FeedBackMessage extends BaseMessage<FeedBack>{

	private String managerEmaill;
	
	public FeedBackMessage(String managerEmaill) throws MessageException {
		super(null);
		this.managerEmaill = managerEmaill;
	}
	@Override
	protected String getRecipient(FeedBack feedBack) throws MessageException {
		return managerEmaill;
	}
	
	@Override
	protected String getText(FeedBack feedBack) throws MessageException {
		return feedBack.getMessage() + 
				"/n email: " + feedBack.getEmail();
	}
	
	@Override
	protected String getSubject() throws MessageException {
		return "Message from custommer";
	}
}
