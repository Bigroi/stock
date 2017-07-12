package com.bigroi.stock.mail;

import static com.bigroi.stock.bean.common.Constant.EMAIL_PASS;
import static com.bigroi.stock.bean.common.Constant.EMAIL_USER;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailManager extends PropertiesMail implements MailPost {

	private String user;
	private String password;

	public MailManager() {

		this(EMAIL_USER, EMAIL_PASS);

	}

	public MailManager(String user, String password) {
		this.user = user;
		this.password = password;
	}

	@Override
	public void send(String toEmail, String subject, String text) {
		send(EMAIL_USER, toEmail, subject, text);
	}

	@Override
	public void send(String fromEmail, String toEmail, String subject, String text) {
		Session session = Session.getInstance(super.properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			InternetAddress emailFrom = new InternetAddress(fromEmail);
			message.setFrom(emailFrom);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sendToAdmin(String toEmail, String subject, String text) {
		// TODO Auto-generated method stub
	}
}
