package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;

public class TenderExparationMessage extends BaseMessage<Tender> {

	@Autowired
	private CompanyService companyService;
	
	public TenderExparationMessage(String fileName) throws MessageException {
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
				.replaceAll("@price", getDataObject().getMaxPrice() + "")
				.replaceAll("@description", getDataObject().getDescription())
				.replaceAll("@volume", getDataObject().getMaxVolume() + "");
	}

}
