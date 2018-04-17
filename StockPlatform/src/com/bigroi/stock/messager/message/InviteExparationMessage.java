package com.bigroi.stock.messager.message;


import java.util.Map;

import com.bigroi.stock.messager.MessagerFactory;


public class InviteExparationMessage extends BaseMessage<Map<String, String>>{

	protected InviteExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}
	@Override
	protected String getEmail() throws MessageException {
			return getDataObject().get("email");
	}
	
	protected String getText() throws MessageException {
		String url = MessagerFactory.getMailManager().getServerAdress();
		Map<String, String> map = getDataObject();
		url += "account/Join.spr?code="+map.get("code");
		return super.getText()
				.replaceAll("@link", url);
	}
}
