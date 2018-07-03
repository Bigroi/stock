package com.bigroi.stock.messager.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.messager.MailManagerException;

abstract class BaseMessage<T> implements Message<T>{
	
	@Autowired
	private EmailDao emailDao;
	
	@Autowired
	protected MailManager mailManager;
	
	private String subject;
	
	private String text;	
	
	protected BaseMessage(String fileName) throws MessageException {
		try{
			if (fileName != null && !fileName.equals(""))
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
	
	public void sendImediatly(T object) throws MessageException{
		try {
			mailManager.send(getEmail(object));
		} catch (MailManagerException e) {
			throw new MessageException(e);
		}
	}
	
	private Email getEmail(T object) throws MessageException{
		Email email = new Email();
		email.setBody(getText(object));
		email.setRecipient(getRecipient(object));
		email.setSubject(getSubject());
		email.setFile(getFile(object));
		email.setFileName(getFileName());
		return email;
	}
	
	protected byte[] getFile(T object) throws MessageException {
		return null;
	}

	protected String getFileName() {
		return null;
	}

	protected abstract String getRecipient(T object) throws MessageException;
	
	protected String getText(T object) throws MessageException{
		return text;
	}
	
	protected String getSubject() throws MessageException{
		return subject;
	}
	
	public void send(T object) throws MessageException{
		try {
			emailDao.add(getEmail(object));
		} catch (DaoException e) {
			throw new MessageException(e);
		}
	}
}
