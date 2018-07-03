package com.bigroi.stock.messager.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;

public class DealConfirmationMessageForCustomer extends BaseMessage<Deal> {

	@Autowired
	private CompanyDao companyDao;
	
//	@Autowired
//	private DealDocument dealDocument;
	
	public DealConfirmationMessageForCustomer(String fileName) throws MessageException {
		super(fileName);
	}
	
//	@Override
//	protected byte[] getFile(Deal deal) throws MessageException {
//		try {
//			return dealDocument.getDocument(deal);
//		} catch (DocumentException e) {
//			throw new MessageException(e);
//		}
//	}
//	
//	@Override
//	protected String getFileName() {
//		return "Deal.doc";
//	}

	@Override
	protected String getRecipient(Deal deal) throws MessageException {
		try{
			return companyDao.getById(deal.getBuyerAddress().getCompanyId()).getEmail();
		}catch (DaoException e) {
			throw new MessageException(e);
		}
	}

	@Override
	protected String getText(Deal deal) throws MessageException {
		return super.getText(deal)
				.replaceAll("@product", deal.getProduct().getName())
				.replaceAll("@price", deal.getPrice() + "")
				.replaceAll("@server", mailManager.getServerAdress());
	}
}
