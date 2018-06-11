package com.bigroi.stock.messager;

import java.util.Arrays;
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
	private String adminUser;
	private String user;
	private String password;
	private String serverAdress;

	@Override
	public void send(String toEmail, String subject, String text) throws MailManagerException {
		send(user, toEmail, subject, text);
	}

	@Override
	public void send(String fromEmail, String toEmail, String subject, String text) throws MailManagerException {
		try {
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
	public void sendToAdmin(Throwable  e) {
		try{
		String subject = e.getMessage();
		StackTraceElement[] eText = e.getStackTrace();
		String text = Arrays.toString(eText);
		send(adminUser, subject, text);
		}catch (MailManagerException excep) {
			excep.printStackTrace();
		}
	}

	public void setMailProperties(Properties mailProperties) {
		this.mailProperties = mailProperties;
	}
	
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getServerAdress() {
		return serverAdress;
	}
	
	public void setServerAdress(String serverAdress) {
		this.serverAdress = serverAdress;
	}
	
	

}