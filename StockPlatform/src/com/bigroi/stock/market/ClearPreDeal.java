package com.bigroi.stock.market;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.MessagePart;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.util.MessageFromFile;

public class ClearPreDeal implements Runnable {
	private Lot lot;
	private Tender tender;
	private String sellerEmail;
	private String customerEmail;
	private String expiredSubject;
	private String expiredOpponentSubject;
	private String expiredText;
	private String expiredOpponentText;
	private Map<MessagePart, String> expiredLinkMessage;
	private Map<MessagePart, String> expiredLinkOpponentMessage;
	private final static String EXPIRED_LINK_FILE = "expiredLinkUTF8.txt";
	private final static String EXPIRED_LINK_OPPONENT_FILE = "expiredLinkOpponentUTF8.txt";

	@Override
	public void run() {
		try {
			getDataForAllEmailes();
			List<PreDeal> predials = DaoFactory.getPreDealDao().getAllPreDeal();
			for (PreDeal preDeal : predials) {
				if (preDeal.getSellerApprovBool() && preDeal.getCustApprovBool())
					continue;
				getDataForEmail(preDeal);
				setStatuses();
				sendMessages(preDeal);
			}
			DaoFactory.getPreDealDao().deleteAll();
		} catch (DaoException | IOException | MailManagerException e) {
			// TODO Emailing Admin
			e.printStackTrace();
		}
	}

	private void getDataForAllEmailes() throws IOException {
		expiredLinkMessage = MessageFromFile.read(EXPIRED_LINK_FILE);
		expiredLinkOpponentMessage = MessageFromFile.read(EXPIRED_LINK_OPPONENT_FILE);
		expiredSubject = expiredLinkMessage.get(MessagePart.SUBJECT);
		expiredOpponentSubject = expiredLinkOpponentMessage.get(MessagePart.SUBJECT);

	}

	private void sendMessages(PreDeal preDeal) throws MailManagerException {
		if (preDeal.getSellerApprovBool() && !preDeal.getCustApprovBool()) {
			MessagerFactory.getMailManager().send(sellerEmail, expiredOpponentSubject,
					replaceText(expiredOpponentText, preDeal.getLotId()));
			MessagerFactory.getMailManager().send(customerEmail, expiredSubject,
					replaceText(expiredText, preDeal.getTenderId()));
		}

		if (!preDeal.getSellerApprovBool() && preDeal.getCustApprovBool()) {
			MessagerFactory.getMailManager().send(sellerEmail, expiredSubject,
					replaceText(expiredText, preDeal.getLotId()));
			MessagerFactory.getMailManager().send(customerEmail, expiredOpponentSubject,
					replaceText(expiredOpponentText, preDeal.getTenderId()));
		}

		if (!preDeal.getSellerApprovBool() && !preDeal.getCustApprovBool()) {
			MessagerFactory.getMailManager().send(sellerEmail, expiredSubject,
					replaceText(expiredText, preDeal.getLotId()));
			MessagerFactory.getMailManager().send(customerEmail, expiredSubject,
					replaceText(expiredText, preDeal.getTenderId()));
		}
	}

	private void setStatuses() throws DaoException {
		if (lot.isExpired()) {
			lot.setStatus(Status.EXPIRED);
		} else {
			lot.setStatus(Status.IN_GAME);
		}
		DaoFactory.getLotDao().updateById(lot);

		if (tender.isExpired()) {
			tender.setStatus(Status.EXPIRED);
		} else {
			tender.setStatus(Status.IN_GAME);
		}
		DaoFactory.getTenderDao().updateById(tender);

	}

	private void getDataForEmail(PreDeal preDeal) throws DaoException {
		lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
		sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSellerId()).getEmail();

		tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
		customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();

		expiredText = expiredLinkMessage.get(MessagePart.TEXT);
		expiredOpponentText = expiredLinkOpponentMessage.get(MessagePart.TEXT);

	}

	private String replaceText(String text, long id) {
		return text.replaceAll("@id", id + "");
	}

}
