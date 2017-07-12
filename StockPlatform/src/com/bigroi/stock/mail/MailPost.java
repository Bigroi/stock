package com.bigroi.stock.mail;

public interface MailPost {
	
	public void send(String toEmail, String subject, String text);
	
	public void send(String fromEmail,String toEmail, String subject, String text);
	
	public void sendToAdmin(String toEmail, String subject, String text);
	
}
