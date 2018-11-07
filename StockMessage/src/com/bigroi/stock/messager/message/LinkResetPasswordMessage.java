package com.bigroi.stock.messager.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.util.exception.StockRuntimeException;

public class LinkResetPasswordMessage extends BaseMessage<Map<String, String>> {
	
	private StockUser user =  new StockUser();

	public LinkResetPasswordMessage(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(Map<String, String> map) {
		return map.get("email");
	}
	
	@Override
	protected String getText(Map<String, String> map, Locale locale) {
		try {
			String name = getName(map);
			user.setUsername(name);
			String url = mailManager.getServerAdress()
				+ "ResetPassword.spr?code=" 
				+ URLEncoder.encode(map.get("code"), "UTF-8") + "&email=" 
				+ URLEncoder.encode(map.get("email"), "UTF-8");
			return super.getText(map, locale).replaceAll("@link", url);
		} catch (UnsupportedEncodingException e) {
			throw new StockRuntimeException(e);
		}
	}
	
	@Override
	protected String getSubject(Locale locale) {
		return super.getSubject(locale).replaceAll("@username", user.getUsername());
	}
	
	private String getName(Map<String, String> map){
		String email = map.get("email");
		return email.split("@")[0];	
	}
}
