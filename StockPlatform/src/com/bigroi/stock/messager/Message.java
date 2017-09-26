package com.bigroi.stock.messager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.Email;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.util.TradingPrice;

public class Message {
	private String subject;
	private String text;
	private String sellerEmail;
	private String customerEmail;
	private String sellerLinkApprove;
	private String sellerLinkCancel;
	private String customerLinkApprove;
	private String customerLinkCancel;
	private final static String SUCCESS_LINK_FILE = "successUTF8.txt";
	private final static String CANCEL_LINK_FILE = "cancelUTF8.txt";
	private final static String EXPIRED_LINK_FILE = "expiredLinkUTF8.txt";
	private final static String EXPIRED_LINK_OPPONENT_FILE = "expiredLinkOpponentUTF8.txt";
	private final static String CUSTOMER_CONFIRMATION_FILE = "customerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_FILE = "sellerConfirmationUTF8.txt";
	private final static String CHANGE_USER_PASSWORD_FILE = "changeUserPasswordUTF8.txt";
	
	public void sendMessageChangeUserPass(User user) throws IOException, DaoException{	
		getMessageFromFile(CHANGE_USER_PASSWORD_FILE);
		Company company = DaoFactory.getCompanyDao().getById(user.getCompanyId());
		String companyEmail = company.getEmail();
		try {
			MessagerFactory.getMailManager().send(companyEmail, subject, text.replaceAll("@login", user.getLogin() + " ").replaceAll("@password", user.getPassword()+ " "));
		} catch (MailManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessageSuccess(PreDeal preDeal) throws IOException, DaoException, MailManagerException {
		getMessageFromFile(SUCCESS_LINK_FILE);
		getEmails(preDeal);

		DaoFactory.getEmailDao().add(new Email(sellerEmail, subject, text.replaceAll("@id", preDeal.getLotId() + "")));
		DaoFactory.getEmailDao()
				.add(new Email(customerEmail, subject, text.replaceAll("@id", preDeal.getTenderId() + "")));
	}

	public void sendMessageCancelSeller(PreDeal preDeal) throws DaoException, IOException, MailManagerException {
		getMessageFromFile(CANCEL_LINK_FILE);
		getEmails(preDeal);
		DaoFactory.getEmailDao()
				.add(new Email(customerEmail, subject, text.replaceAll("@id", preDeal.getTenderId() + "")));		
	}

	public void sendMessageCancelCustomer(PreDeal preDeal) throws DaoException, IOException, MailManagerException {
		getMessageFromFile(CANCEL_LINK_FILE);
		getEmails(preDeal);
		DaoFactory.getEmailDao()
				.add(new Email(customerEmail, subject, text.replaceAll("@id", preDeal.getLotId() + "")));
	}

	public void sendMessageClearPredeal(PreDeal preDeal) throws IOException, MailManagerException, DaoException {
		getMessageFromFile(EXPIRED_LINK_FILE);
		String expiredSubject = subject;
		String expiredText = text;
		getMessageFromFile(EXPIRED_LINK_OPPONENT_FILE);
		String expiredOpponentSubject = subject;
		String expiredOpponentText = text;
		getEmails(preDeal);

		if (preDeal.getSellerApprovBool() && !preDeal.getCustApprovBool()) {
			DaoFactory.getEmailDao().add(new Email(sellerEmail, expiredOpponentSubject,
					expiredOpponentText.replaceAll("@id", preDeal.getLotId() + "")));
			DaoFactory.getEmailDao().add(new Email(customerEmail, expiredSubject,
					expiredText.replaceAll("@id", preDeal.getTenderId() + "")));			
		}
		if (!preDeal.getSellerApprovBool() && preDeal.getCustApprovBool()) {
			DaoFactory.getEmailDao().add(
					new Email(sellerEmail, expiredSubject, expiredText.replaceAll("@id", preDeal.getLotId() + "")));
			DaoFactory.getEmailDao().add(new Email(customerEmail, expiredOpponentSubject,
					expiredOpponentText.replaceAll("@id", preDeal.getTenderId() + "")));
		}
		if (!preDeal.getSellerApprovBool() && !preDeal.getCustApprovBool()) {
			DaoFactory.getEmailDao().add(
					new Email(sellerEmail, expiredSubject, expiredText.replaceAll("@id", preDeal.getLotId() + "")));
			DaoFactory.getEmailDao().add(new Email(customerEmail, expiredSubject,
					expiredText.replaceAll("@id", preDeal.getTenderId() + "")));			
		}
	}

	public void sendMessageForConfirmation(PreDeal preDeal) throws IOException, DaoException, MailManagerException {
		getMessageFromFile(SELLER_CONFIRMATION_FILE);
		String sellerSubject = subject;
		String sellerText = text;
		getMessageFromFile(CUSTOMER_CONFIRMATION_FILE);
		String customerSubject = subject;
		String customerText = text;
		getEmails(preDeal);
		getLinksForConfirmation(preDeal);

		Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
		Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());

		double price = TradingPrice.getPrice(DaoFactory.getLotDao().getById(preDeal.getLotId()).getMinPrice(),
				DaoFactory.getTenderDao().getById(preDeal.getTenderId()).getMaxPrice());
		sellerText = sellerText.replaceAll("@lotId", preDeal.getLotId() + "").replaceAll("@lotDate", lot.getDateStr())
				.replaceAll("@lotPrice", lot.getMinPrice() + "").replaceAll("@price", price + "")
				.replaceAll("@sellerLinkApprove", sellerLinkApprove).replaceAll("@sellerLinkCancel", sellerLinkCancel);
		customerText = customerText.replaceAll("@tenderId", preDeal.getTenderId() + "")
				.replaceAll("@tenderDate", tender.getDateStr()).replaceAll("@tenderPrice", tender.getMaxPrice() + "")
				.replaceAll("@price", price + "").replaceAll("@customerLinkApprove", customerLinkApprove)
				.replaceAll("@customerLinkCancel", customerLinkCancel);

		DaoFactory.getEmailDao().add(new Email(sellerEmail, sellerSubject, sellerText));
		DaoFactory.getEmailDao().add(new Email(customerEmail, customerSubject, customerText));		
	}

	private void getLinksForConfirmation(PreDeal preDeal) {
		String sellerLink = MessagerFactory.getLink().getSellerConfirmationLink() + "?id=" + preDeal.getId() + "&key="
				+ preDeal.getSellerHashCode() + "&action=";
		String customerLink = MessagerFactory.getLink().getCustomerConfirmationLink() + "?id=" + preDeal.getId()
				+ "&key=" + preDeal.getCustomerHashCode() + "&action=";
		sellerLinkApprove = sellerLink + Action.APPROVE;
		sellerLinkCancel = sellerLink + Action.CANCEL;
		customerLinkApprove = customerLink + Action.APPROVE;
		customerLinkCancel = customerLink + Action.CANCEL;

	}

	private void getMessageFromFile(String fileName) throws IOException {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName),
						StandardCharsets.UTF_8))) {
			subject = null;
			StringBuilder textBuld = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (subject == null) {
					subject = line;
				} else if (textBuld.length() == 0) {
					textBuld.append(line);
				} else {
					textBuld.append(System.lineSeparator()).append(line);
				}
			}
			text = textBuld.toString();
		}
	}

	private void getEmails(PreDeal preDeal) throws DaoException {
		// TODO одним запросом
		Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
		sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSellerId()).getEmail();
		// TODO одним запросом
		Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
		customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();
	}
}
