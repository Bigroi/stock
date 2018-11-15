package com.bigroi.stock.messager.message;

import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.dao.LabelDao;
import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.util.LabelUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public abstract class BaseMessage<T> implements Message<T>{
	
	private static final Logger LOGGER = Logger.getLogger(BaseMessage.class);
	
	@Autowired
	private EmailDao emailDao;
	
	@Autowired
	protected MailManager mailManager;
	
	@Autowired
	protected LabelDao labelDao;
	
	private final Map<Locale, MessageTemplate> messageTemplates;
	
	protected abstract String getText(T object, Locale locale);
	
	protected BaseMessage(){
		messageTemplates = null;
	}
	
	protected BaseMessage(String fileName, String fileExtention) {
		Builder<Locale, MessageTemplate> builder = ImmutableMap.builder();
		for (Locale locale : LabelUtil.getPassibleLanguages(null)){
			builder.put(locale, new MessageTemplate(fileName, locale, fileExtention));
		}
		messageTemplates = builder.build();
	}
		
		
	public void sendImediatly(T object, Locale locale){
		mailManager.send(getEmail(object, locale));
	}
	
	private Email getEmail(T object, Locale locale) {
		Email email = new Email();
		email.setBody(getText(object, locale));
		email.setRecipient(getRecipient(object));
		email.setSubject(getSubject(locale)); 
		email.setFile(getFile(object));
		email.setFileName(getFileName());
		return email;
	}
	
	protected byte[] getFile(T object){
		LOGGER.trace(object);
		return new byte[0];
	}

	protected String getFileName() {
		return null;
	}

	protected abstract String getRecipient(T object);
	
	protected final String getTextTemplate(Locale locale) {
		return messageTemplates.get(locale).getText();
	}
	
	protected String getSubject(Locale locale) {
		return messageTemplates.get(locale).getSubject();
	}
	
	public boolean send(T object, Locale locale){
		 emailDao.add(getEmail(object, locale)); 
		 return true;
	}
	
}