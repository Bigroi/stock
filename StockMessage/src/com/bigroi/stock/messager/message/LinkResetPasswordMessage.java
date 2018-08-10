package com.bigroi.stock.messager.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.bigroi.stock.util.exception.StockRuntimeException;

public class LinkResetPasswordMessage extends BaseMessage<Map<String, String>> {

	public LinkResetPasswordMessage(String fileName){
		super(fileName);
	}

	@Override
	protected String getRecipient(Map<String, String> map) {
		return map.get("email");
	}

	@Override
	protected String getText(Map<String, String> map) {
		try {
			String url = mailManager.getServerAdress()
				+ "ResetPassword.spr?code=" 
				+ URLEncoder.encode(map.get("code"), "UTF-8") + "&email=" 
				+ URLEncoder.encode(map.get("email"), "UTF-8");
			return super.getText(map).replaceAll("@link", url);
		} catch (UnsupportedEncodingException e) {
			throw new StockRuntimeException(e);
		}
		
	}
}
