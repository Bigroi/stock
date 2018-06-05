package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;

public class DealExparationMessageForSeller extends BaseMessage<Deal>{

	@Autowired
	private CompanyService companyService;
	
	public DealExparationMessageForSeller(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getText(Deal deal) throws MessageException {
		return super.getText(deal)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@server", mailManager.getServerAdress());
	}
	
	@Override
	protected String getEmail(Deal deal) throws MessageException {
		try{
			return companyService.getCompanyById(
					deal.getSellerAddress().getCompanyId()
					).getEmail();
		}catch (ServiceException e) {
			throw new MessageException(e);
		}
	}

	
}
