package com.bigroi.stock.messager.message;

import com.bigroi.stock.bean.ui.FeedBack;

public class FeedBackMessage extends BaseMessage<FeedBack>{

	private String managerEmaill;
	
	public FeedBackMessage(String managerEmaill) throws MessageException {
		super(null);
		this.managerEmaill = managerEmaill;
	}
	@Override
	protected String getEmail() throws MessageException {
		return managerEmaill;
	}
	
	protected String getText() throws MessageException {
		return getDataObject().getMessage() + 
				"/n email: " + getDataObject().getEmail();
	}
	
	@Override
	protected String getSubject() throws MessageException {
		return "Message from " + getDataObject().getEmail();
	}
}
