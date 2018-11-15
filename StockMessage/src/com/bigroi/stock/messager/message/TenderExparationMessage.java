package com.bigroi.stock.messager.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.CompanyDao;

public class TenderExparationMessage extends BaseMessage<Tender> {

	@Autowired
	private CompanyDao companyDao;
	
	public TenderExparationMessage(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(Tender tender) {
		long companyId = tender.getCompanyId();
		return companyDao.getById(companyId).getEmail();
	}
	
	@Override
	protected String getText(Tender tender, Locale locale) {
		return getTextTemplate(locale)
				.replaceAll("@price", tender.getPrice() + "")
				.replaceAll("@description", tender.getDescription())
				.replaceAll("@volume", tender.getMaxVolume() + "");
	}

}
