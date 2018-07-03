package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;

public class TenderExparationMessage extends BaseMessage<Tender> {

	@Autowired
	private CompanyDao companyDao;
	
	public TenderExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getRecipient(Tender tender) throws MessageException {
		try {
			long companyId = tender.getCompanyId();
			return companyDao.getById(companyId).getEmail();
		} catch (DaoException e) {
			throw new MessageException(e);
		}
	}
	
	@Override
	protected String getText(Tender tender) throws MessageException {
		return super.getText(tender)
				.replaceAll("@price", tender.getMaxPrice() + "")
				.replaceAll("@description", tender.getDescription())
				.replaceAll("@volume", tender.getMaxVolume() + "");
	}

}
