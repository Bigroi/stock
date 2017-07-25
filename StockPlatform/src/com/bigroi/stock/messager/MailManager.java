package com.bigroi.stock.messager;

public interface MailManager {

	public void send(String toEmail, String subject, String text) throws MailManagerException;

	public void send(String fromEmail, String toEmail, String subject, String text) throws MailManagerException;

	public void sendToAdmin(Throwable e);

}
