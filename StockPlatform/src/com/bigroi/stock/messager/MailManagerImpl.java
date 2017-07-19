package com.bigroi.stock.messager;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailManagerImpl implements MailManager {

	private Properties mailProperties;
	private String user;
	private String password;

	@Override
	public void send(String toEmail, String subject, String text) throws MailManagerException {
		send(user, toEmail, subject, text);
	}

	@Override
	public void send(String fromEmail, String toEmail, String subject, String text) throws MailManagerException {
		try {
			mailProperties.put("mail.smtp.port", 587);
			Session session = Session.getInstance(mailProperties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
			Message message = new MimeMessage(session);
			InternetAddress emailFrom = new InternetAddress(fromEmail);
			message.setFrom(emailFrom);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new MailManagerException(e);
		}
	}

	@Override
	public void sendToAdmin(String subject, String text) throws MailManagerException {
		// do not throw any Exception! In case of exception jest log it.
		try {
			mailProperties.put("mail.smtp.port", 587);
			Session session = Session.getInstance(mailProperties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user));
			Exception eSubject = new Exception(subject);
			eSubject.getMessage().getClass();
			Exception eText = new Exception(text);
			eText.getStackTrace();
			message.setSubject(String.valueOf(eSubject));
			message.setText(String.valueOf(eText));
			Transport.send(message);
		} catch (MessagingException e) {
			throw new MailManagerException(e);
		}
	}

	public void setMailProperties(Properties mailProperties) {
		this.mailProperties = mailProperties;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) {

	}
}
