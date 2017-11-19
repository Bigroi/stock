package com.bigroi.stock.service.impl;

import java.util.List;

import com.bigroi.stock.bean.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.ServiceException;

public class MessageServiceImpl implements MessageService{

	private EmailDao emailDao;
	
	public void setEmailDao(EmailDao emailDao) {
		this.emailDao = emailDao;
	}
	
	@Override
	public void sendAllEmails() throws ServiceException{
		try {
			List<Email> emails;
			do {
				emails = emailDao.getAll();
				for (Email email : emails) {
					MessagerFactory.getMailManager().send(email.getToEmail(), email.getEmailSubject(), email.getEmailText());
					emailDao.deleteById(email.getId());
				}
			} while (!emails.isEmpty());
			
		} catch (DaoException | MailManagerException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void add(Email email) throws ServiceException {
		try {
			emailDao.add(email);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}
	
}
