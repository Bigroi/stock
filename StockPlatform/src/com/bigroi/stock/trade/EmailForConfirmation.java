package com.bigroi.stock.trade;

import static com.bigroi.stock.bean.common.Constant.EMAIL_PASS;
import static com.bigroi.stock.bean.common.Constant.EMAIL_USER;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.bean.common.MessagePart;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.mail.MailManager;
import com.bigroi.stock.util.MessageFromFile;

public class EmailForConfirmation {
	private final static String CUSTOMER_CONFIRMATION_FILE = "customerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_FILE = "sellerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_LINK = "http://localhost:8080/StockPlatform/SellerCheck.spr?";
	private final static String CUSTOMER_CONFIRMATION_LINK = "http://localhost:8080/StockPlatform/CustomerCheck.spr?";
	
	public void send() throws IOException, DaoException {
		Map<MessagePart, String> customerMessage = MessageFromFile.read(CUSTOMER_CONFIRMATION_FILE);
		Map<MessagePart, String> sellerMessage = MessageFromFile.read(SELLER_CONFIRMATION_FILE);
		List<PreDeal> preDials = DaoFactory.getPreDealDao().getAllPreDeal();

		for (PreDeal preDeal : preDials) {
			Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
			String sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSalerId()).getEmail();

			Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
			String customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();			
			
			String sellerSubject = sellerMessage.get(MessagePart.SUBJECT);
			String customerSubject = customerMessage.get(MessagePart.SUBJECT);
			String sellerText = sellerMessage.get(MessagePart.TEXT);
			String customerText = customerMessage.get(MessagePart.TEXT);

			String sellerLink = SELLER_CONFIRMATION_LINK + "id=" + preDeal.getId() + "&key="
					+ preDeal.getSellerHashCode() + "&action=";
			String customerLink = CUSTOMER_CONFIRMATION_LINK + "id=" + preDeal.getId() + "&key="
					+ preDeal.getCustomerHashCode() + "&action=";

			String sellerLinkApprove = sellerLink + Action.APPROVE;
			String sellerLinkCancel = sellerLink + Action.CANCEL;
			String customerLinkApprove = customerLink + Action.APPROVE;
			String customerLinkCancel = customerLink + Action.CANCEL;

			double minPrace = DaoFactory.getLotDao().getById(preDeal.getLotId()).getMinPrice();
			double maxPrice = DaoFactory.getTenderDao().getById(preDeal.getTenderId()).getMaxPrice();
			double price = (minPrace + maxPrice) / 2;

			sellerText = sellerText.replaceAll("@lotId", preDeal.getLotId() + "");
			sellerText = sellerText.replaceAll("@lotDate", lot.getDateStr());
			sellerText = sellerText.replaceAll("@lotPrice", lot.getMinPrice() + "");
			sellerText = sellerText.replaceAll("@price", price + "");
			sellerText = sellerText.replaceAll("@sellerLinkApprove", sellerLinkApprove);
			sellerText = sellerText.replaceAll("@sellerLinkCancel", sellerLinkCancel);

			customerText = customerText.replaceAll("@tenderId", preDeal.getTenderId() + "");
			customerText = customerText.replaceAll("@tenderDate", tender.getDateStr());
			customerText = customerText.replaceAll("@tenderPrice", tender.getMaxPrice() + "");
			customerText = customerText.replaceAll("@price", price + "");
			customerText = customerText.replaceAll("@customerLinkApprove", customerLinkApprove);
			customerText = customerText.replaceAll("@customerLinkCancel", customerLinkCancel);			
			
			lot.setStatus(Status.SUCCESS);
			DaoFactory.getLotDao().update(lot.getId(), lot);
			tender.setStatus(Status.SUCCESS);
			DaoFactory.getTenderDao().update(tender.getId(), tender);
		
			new MailManager(EMAIL_USER, EMAIL_PASS).send(sellerEmail, sellerSubject, sellerText);
			new MailManager(EMAIL_USER, EMAIL_PASS).send(customerEmail, customerSubject, customerText);			
		}
	}	
}
