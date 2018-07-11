package com.bigroi.stock.messager.message.deal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.messager.message.BaseMessage;
import com.bigroi.stock.messager.message.MessageException;

public abstract class DealBaseMessage extends BaseMessage<Deal>{

	@Autowired
	private CompanyDao companyDao;
	
	protected DealBaseMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected final String getRecipient(Deal deal) throws MessageException {
		try {
			return companyDao.getById(getCompanyId(deal)).getEmail();																						
		} catch (DaoException e) {
			throw new MessageException(e);
		}
	}
	
	protected abstract long getCompanyId(Deal deal);
}
