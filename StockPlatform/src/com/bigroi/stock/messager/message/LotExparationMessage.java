package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;

public class LotExparationMessage extends BaseMessage<Lot> {

	@Autowired
	private CompanyService companyService;
	
	public LotExparationMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
		try {
			long companyId = getDataObject().getCompanyId();
			return companyService.getCompanyById(companyId).getEmail();
		} catch (ServiceException e) {
			throw new MessageException(e);
		}
	}
	
	@Override
	protected String getText() throws MessageException {
		return super.getText()
				.replaceAll("@price", getDataObject().getMinPrice() + "")
				.replaceAll("@description", getDataObject().getDescription())
				.replaceAll("@volume", getDataObject().getMaxVolume() + "");
	}

}
