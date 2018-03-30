package com.bigroi.stock.messager.message;

import java.util.Map;

import com.bigroi.stock.messager.MessagerFactory;

public class LinkResetPasswordMessage extends BaseMessage<Map<String, String>> {

	public LinkResetPasswordMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		return getDataObject().get("email");
	}

	@Override
	protected String getText() throws MessageException {
		Map<String, String> map = getDataObject();
		String url = MessagerFactory.getMailManager().getServerAdress();
		url += "account/ResetPassword.spr?code=" + map.get("code") + "&email=" + map.get("email");
		return super.getText().replaceAll("@link", url);
	}
}
