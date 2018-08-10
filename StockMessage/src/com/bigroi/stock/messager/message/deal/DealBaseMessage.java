package com.bigroi.stock.messager.message.deal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.messager.message.BaseMessage;

public abstract class DealBaseMessage extends BaseMessage<Deal>{

	@Autowired
	private CompanyDao companyDao;
	
	protected DealBaseMessage(String fileName) {
		super(fileName);
	}

	@Override
	protected final String getRecipient(Deal deal) {
		return companyDao.getById(getCompanyId(deal)).getEmail();																						
	}
	
	protected abstract long getCompanyId(Deal deal);
}
