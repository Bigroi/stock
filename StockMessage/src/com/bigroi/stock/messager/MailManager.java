package com.bigroi.stock.messager;

import com.bigroi.stock.bean.db.Email;

public interface MailManager {

	public void send(Email email) throws MailManagerException;

	public void send(String fromEmail, Email email) throws MailManagerException;

	public void sendToAdmin(Throwable e);
	
    public String getServerAdress();

}
