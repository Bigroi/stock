package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.service.MessageService;

@Repository
public class MessageServiceImpl implements MessageService{

	@Autowired
	private EmailDao emailDao;
	
	@Autowired
	private MailManager mailManager;
	
	@Override
	public void sendAllEmails(){
		List<Email> emails;
		do {
			emails = emailDao.getAll();
			for (Email email : emails) {
				mailManager.send(email);
				emailDao.deleteById(email.getId());
			}
		} while (!emails.isEmpty());
	}

	@Override
	public void add(Email email) {
		emailDao.add(email);
	}
	
}
