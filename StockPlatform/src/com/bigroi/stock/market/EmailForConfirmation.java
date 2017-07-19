package com.bigroi.stock.market;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bigroi.stock.bean.Link;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.bean.common.MessagePart;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.util.MessageFromFile;

public class EmailForConfirmation {
	private Lot lot;
	private Tender tender;
	private Map<MessagePart, String> customerMessage;
	private Map<MessagePart, String> sellerMessage;
	private Link link;
	private String sellerEmail;
	private String customerEmail;
	private String sellerText;
	private String customerText;
	private String sellerSubject;
	private String customerSubject;
	private String sellerLinkApprove;
	private String sellerLinkCancel;
	private String customerLinkApprove;
	private String customerLinkCancel;
	private final static String CUSTOMER_CONFIRMATION_FILE = "customerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_FILE = "sellerConfirmationUTF8.txt";
	
	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring-mail.xml");
	
	public void send() throws MarketException {		
		try{
			getDataForAllEmailes();			
			List<PreDeal> preDials = DaoFactory.getPreDealDao().getAllPreDeal();	
			for (PreDeal preDeal : preDials) {				
				getDataForEmail(preDeal);				
				getLinks(preDeal);	
				getText(preDeal);			
				setStatuses();					
				sendMessages();							
			}		
		}catch (MailManagerException | DaoException | IOException e) {
			throw new MarketException(e);
		}
	}

	private void sendMessages() throws MailManagerException {
		MessagerFactory.getMailManager().send(sellerEmail, sellerSubject, sellerText);
		MessagerFactory.getMailManager().send(customerEmail, customerSubject, customerText);	
		
	}

	private void setStatuses() throws DaoException {
		lot.setStatus(Status.SUCCESS);
		DaoFactory.getLotDao().updateById(lot);
		tender.setStatus(Status.SUCCESS);
		DaoFactory.getTenderDao().updateById(tender);
		
	}

	private void getText(PreDeal preDeal) throws DaoException {
		double minPrace = DaoFactory.getLotDao().getById(preDeal.getLotId()).getMinPrice();
		double maxPrice = DaoFactory.getTenderDao().getById(preDeal.getTenderId()).getMaxPrice();
		double price = (minPrace + maxPrice) / 2;		
		sellerText = sellerText
				.replaceAll("@lotId", preDeal.getLotId() + "")
				.replaceAll("@lotDate", lot.getDateStr())
				.replaceAll("@lotPrice", lot.getMinPrice() + "")
				.replaceAll("@price", price + "")
				.replaceAll("@sellerLinkApprove", sellerLinkApprove)
				.replaceAll("@sellerLinkCancel", sellerLinkCancel);
		customerText = customerText
				.replaceAll("@tenderId", preDeal.getTenderId() + "")
				.replaceAll("@tenderDate", tender.getDateStr())
				.replaceAll("@tenderPrice", tender.getMaxPrice() + "")
				.replaceAll("@price", price + "")
				.replaceAll("@customerLinkApprove", customerLinkApprove)
				.replaceAll("@customerLinkCancel", customerLinkCancel);	
		
	}

	private void getLinks(PreDeal preDeal) {
		String sellerLink = link.getSellerConfirmationLink() + "?id=" + preDeal.getId() + "&key="
				+ preDeal.getSellerHashCode() + "&action=";
		String customerLink = link.getCustomerConfirmationLink() + "?id=" + preDeal.getId() + "&key="
				+ preDeal.getCustomerHashCode() + "&action=";
		sellerLinkApprove = sellerLink + Action.APPROVE;
		sellerLinkCancel = sellerLink + Action.CANCEL;
		customerLinkApprove = customerLink + Action.APPROVE;
		customerLinkCancel = customerLink + Action.CANCEL;		
	}

	private void getDataForEmail(PreDeal preDeal) throws DaoException {
		lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
		sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSellerId()).getEmail();		
		tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
		customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();		
		sellerText = sellerMessage.get(MessagePart.TEXT);
		customerText = customerMessage.get(MessagePart.TEXT);		
	}

	private void getDataForAllEmailes() throws IOException {
		customerMessage = MessageFromFile.read(CUSTOMER_CONFIRMATION_FILE);
		sellerMessage = MessageFromFile.read(SELLER_CONFIRMATION_FILE);
		link = (Link) CONTEX.getBean("link");		
		sellerSubject = sellerMessage.get(MessagePart.SUBJECT);
		customerSubject = customerMessage.get(MessagePart.SUBJECT);		
	}
}
