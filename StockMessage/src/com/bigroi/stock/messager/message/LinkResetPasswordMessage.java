package com.bigroi.stock.messager.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.util.exception.StockRuntimeException;

public class LinkResetPasswordMessage extends BaseMessage<Map<String, String>> {
	
	private static final String EMAIL = "email";
	private StockUser user =  new StockUser();

	public LinkResetPasswordMessage(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(Map<String, String> map) {
		return map.get(EMAIL);
	}
	
	@Override
	protected String getText(Map<String, String> map, Locale locale) {
		try {
			String name = getName(map);
			user.setUsername(name);
			String url = mailManager.getServerAdress()
				+ "ResetPassword.spr?code=" 
				+ URLEncoder.encode(map.get("code"), "UTF-8") + "&email=" 
				+ URLEncoder.encode(map.get(EMAIL), "UTF-8");
			return getTextTemplate(locale).replaceAll("@link", url);
		} catch (UnsupportedEncodingException e) {
			throw new StockRuntimeException(e);
		}
	}
	
	@Override
	protected String getSubject(Locale locale) {
		return super.getSubject(locale).replaceAll("@username", user.getUsername());
	}
	
	private String getName(Map<String, String> map){
		String email = map.get(EMAIL);
		return email.split("@")[0];	
	}
}
