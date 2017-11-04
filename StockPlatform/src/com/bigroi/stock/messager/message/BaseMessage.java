package com.bigroi.stock.messager.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.bigroi.stock.bean.Email;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

public abstract class BaseMessage<T> implements Message<T>{
	
	private String subject;
	
	private String text;	
	
	private T dataObject;
	
	public T getDataObject() {
		return dataObject;
	}
	
	public void setDataObject(T dataObject) {
		this.dataObject = dataObject;
	}
	
	protected BaseMessage(String fileName) throws MessageException {
		try{
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName),
							StandardCharsets.UTF_8))) {
				subject = null;
				StringBuilder textBuld = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					if (subject == null) {
						subject = line;
					} else if (textBuld.length() == 0) {
						textBuld.append(line);
					} else {
						textBuld.append(System.lineSeparator()).append(line);
					}
				}
				text = textBuld.toString();
			}
		}catch (IOException e) {
			throw new MessageException(e);
		}
	}
	
	public void sendImediatly() throws MessageException{
		try {
			MessagerFactory.getMailManager().send(getEmail(), getSubject(), getText());
		} catch (MailManagerException e) {
			throw new MessageException(e);
		}
	}
	
	protected abstract String getEmail() throws MessageException;
	
	protected String getText() throws MessageException{
		return text;
	}
	
	protected String getSubject() throws MessageException{
		return subject;
	}
	
	public void send() throws MessageException{
		try {
			ServiceFactory.getMessageService().add(new Email(getEmail(), getSubject(), getText()));
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
}
