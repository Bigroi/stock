package com.bigroi.stock.messager.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.dao.CompanyDao;

public class LotExparationMessage extends BaseMessage<Lot> {

	@Autowired
	private CompanyDao companyDao;
	
	public LotExparationMessage(String fileName, String fileExtention){
		super(fileName, fileExtention);
	}

	@Override
	protected String getRecipient(Lot lot) {
		long companyId = lot.getCompanyId();
		return companyDao.getById(companyId).getEmail();
	}
	
	@Override
	protected String getText(Lot lot, Locale locale){
		return getTextTemplate(locale)
				.replaceAll("@price", lot.getPrice() + "")
				.replaceAll("@description", lot.getDescription())
				.replaceAll("@volume", lot.getMaxVolume() + "");
	}

}
