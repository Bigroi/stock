package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;

public class LotExparationMessage extends BaseMessage<Lot> {

	@Autowired
	private CompanyDao companyDao;
	
	public LotExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getRecipient(Lot lot) throws MessageException {
		try {
			long companyId = lot.getCompanyId();
			return companyDao.getById(companyId).getEmail();
		} catch (DaoException e) {
			throw new MessageException(e);
		}
	}
	
	@Override
	protected String getText(Lot lot) throws MessageException {
		return super.getText(lot)
				.replaceAll("@price", lot.getMinPrice() + "")
				.replaceAll("@description", lot.getDescription())
				.replaceAll("@volume", lot.getMaxVolume() + "");
	}

}
