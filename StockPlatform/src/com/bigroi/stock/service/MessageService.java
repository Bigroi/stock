package com.bigroi.stock.service;

import com.bigroi.stock.bean.Email;

public interface MessageService {

	void sendAllEmails() throws ServiceException;

	void add(Email email) throws ServiceException;

}
