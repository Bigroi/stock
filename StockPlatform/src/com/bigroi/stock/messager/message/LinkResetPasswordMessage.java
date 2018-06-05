package com.bigroi.stock.messager.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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
		try {
			Map<String, String> map = getDataObject();
			String url = mailManager.getServerAdress();
			url += "account/ResetPassword.spr?code=" 
					+ URLEncoder.encode(map.get("code"), "UTF-8") + "&email=" 
					+ URLEncoder.encode(map.get("email"), "UTF-8");
			return super.getText().replaceAll("@link", url);
		} catch (UnsupportedEncodingException e) {
			throw new MessageException(e);
		}
		
	}
}
