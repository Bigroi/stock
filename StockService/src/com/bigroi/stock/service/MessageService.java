package com.bigroi.stock.service;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Email;

@Service
public interface MessageService {

	void sendAllEmails();

	void add(Email email);

}
