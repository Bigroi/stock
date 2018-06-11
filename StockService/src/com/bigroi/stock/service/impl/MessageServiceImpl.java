package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class MessageServiceImpl implements MessageService{

	@Autowired
	private EmailDao emailDao;
	
	@Autowired
	private MailManager mailManager;
	
	@Override
	public void sendAllEmails() throws ServiceException{
		try {
			List<Email> emails;
			do {
				emails = emailDao.getAll();
				for (Email email : emails) {
					mailManager.send(email.getRecipient(), email.getSubject(), email.getBody());
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
