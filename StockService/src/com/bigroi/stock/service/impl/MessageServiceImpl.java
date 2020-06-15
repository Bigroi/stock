package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.service.MessageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    private final EmailDao emailDao;
    private final EmailClient emailClient;

    public MessageServiceImpl(EmailDao emailDao, EmailClient emailClient) {
        this.emailDao = emailDao;
        this.emailClient = emailClient;
    }

    @Override
    public void sendAllEmails() {
        List<Email> emails;
        do {
            emails = emailDao.getAll();
            emails.forEach(this::sendEmail);
        } while (!emails.isEmpty());
    }

    @Transactional
    public void sendEmail(Email email) {
        emailDao.deleteById(email.getId());
        emailClient.sendMessageNow(email);
    }

}
