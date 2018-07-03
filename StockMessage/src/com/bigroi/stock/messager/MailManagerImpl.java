package com.bigroi.stock.messager;

import java.util.Arrays;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.bigroi.stock.bean.db.Email;

public class MailManagerImpl implements MailManager {

	private Properties mailProperties;
	private String adminUser;
	private String user;
	private String password;
	private String serverAdress;

	@Override
	public void send(Email email) throws MailManagerException {
		send(user, email);
	}

	@Override
	public void send(String fromEmail, Email email) throws MailManagerException {
		try {
			Session session = Session.getInstance(mailProperties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
			Message message = new MimeMessage(session);
			InternetAddress emailFrom = new InternetAddress(fromEmail);
			message.setFrom(emailFrom);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
			message.setSubject(email.getSubject());
			message.setText(email.getBody());
			if (email.getFile() != null){
				MimeBodyPart messageBodyPart = new MimeBodyPart();
	
		        Multipart multipart = new MimeMultipart();
	
		        messageBodyPart = new MimeBodyPart();
		        DataSource source = new ByteArrayDataSource(email.getFile(), "");
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(email.getFileName());
		        multipart.addBodyPart(messageBodyPart);
	
		        message.setContent(multipart);
			}	
			Transport.send(message);
		} catch (MessagingException e) {
			throw new MailManagerException(e);
		}
	}

	@Override
	public void sendToAdmin(Throwable e) {
		try{
		Email email = new Email();
		email.setBody(Arrays.toString(e.getStackTrace()));
		email.setRecipient(adminUser);
		email.setSubject(e.getMessage());
		send(email);
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