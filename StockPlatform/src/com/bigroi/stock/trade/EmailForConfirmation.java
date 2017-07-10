package com.bigroi.stock.trade;

import static com.bigroi.stock.bean.common.Constant.EMAIL_PASS;
import static com.bigroi.stock.bean.common.Constant.EMAIL_USER;

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
import com.bigroi.stock.mail.MailManager;
import com.bigroi.stock.util.MessageFromFile;

public class EmailForConfirmation {
	private final static String CUSTOMER_CONFIRMATION_FILE = "customerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_FILE = "sellerConfirmationUTF8.txt";
	
	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring.xml");
	
	public void send() throws IOException, DaoException {		
		Map<MessagePart, String> customerMessage = MessageFromFile.read(CUSTOMER_CONFIRMATION_FILE);
		Map<MessagePart, String> sellerMessage = MessageFromFile.read(SELLER_CONFIRMATION_FILE);
		Link link = (Link) CONTEX.getBean("link");
		
		List<PreDeal> preDials = DaoFactory.getPreDealDao().getAllPreDeal();
		String sellerSubject = sellerMessage.get(MessagePart.SUBJECT);
		String customerSubject = customerMessage.get(MessagePart.SUBJECT);

		for (PreDeal preDeal : preDials) {
			Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
			String sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSellerId()).getEmail();
			
			Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
			String customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();			
			
			String sellerText = sellerMessage.get(MessagePart.TEXT);
			String customerText = customerMessage.get(MessagePart.TEXT);

			String sellerLink = link.getSellerConfirmationLink() + "id=" + preDeal.getId() + "&key="
					+ preDeal.getSellerHashCode() + "&action=";
			String customerLink = link.getCustomerConfirmationLink() + "id=" + preDeal.getId() + "&key="
					+ preDeal.getCustomerHashCode() + "&action=";

			String sellerLinkApprove = sellerLink + Action.APPROVE;
			String sellerLinkCancel = sellerLink + Action.CANCEL;
			String customerLinkApprove = customerLink + Action.APPROVE;
			String customerLinkCancel = customerLink + Action.CANCEL;

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
			
			lot.setStatus(Status.SUCCESS);
			DaoFactory.getLotDao().updateById(lot);
			tender.setStatus(Status.SUCCESS);
			DaoFactory.getTenderDao().updateById(tender);
		
			new MailManager(EMAIL_USER, EMAIL_PASS).send(sellerEmail, sellerSubject, sellerText);
			new MailManager(EMAIL_USER, EMAIL_PASS).send(customerEmail, customerSubject, customerText);				
		}	
	}	
}
