package com.bigroi.stock.trade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Action;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.util.MessageFromFile;

public class EmailForConfirmation {
	private final static String CUSTOMER_CONFIRMATION_FILE = "customerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_FILE = "sellerConfirmationUTF8.txt";
	private final static String SELLER_CONFIRMATION_LINK = "http://localhost:8080/StockPlatform/SellerCheck.spr?";
	private final static String CUSTOMER_CONFIRMATION_LINK = "http://localhost:8080/StockPlatform/CustomerCheck.spr?";

	public void send() throws IOException, DaoException {
		Map<String, String> customerMessage = MessageFromFile.read(CUSTOMER_CONFIRMATION_FILE);
		Map<String, String> sellerMessage = MessageFromFile.read(SELLER_CONFIRMATION_FILE);
		List<PreDeal> preDials = getAllPreDeal();

		for (PreDeal preDeal : preDials) {
			Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
			String sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSalerId()).getEmail();

			Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
			String customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();			
			
			String sellerSubject = sellerMessage.get("subject");
			String customerSubject = customerMessage.get("subject");
			String sellerText = sellerMessage.get("text");
			String customerText = customerMessage.get("text");

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

			// TODO какие статусы ставить?
			lot.setStatus(Status.ON_APPROVAL);
			DaoFactory.getLotDao().update(lot.getId(), lot);
			tender.setStatus(Status.ON_APPROVAL);
			DaoFactory.getTenderDao().update(tender.getId(), tender);

			sendEmail(sellerEmail, sellerSubject, sellerText);
			sendEmail(customerEmail, customerSubject, customerText);
		}
	}

	// TODO ЗАГЛУШКА для messengera
	private void sendEmail(String email, String subject, String text) {
		System.out.println("To: " + email);
		System.out.println("Subject: " + subject);
		System.out.println("Text: ");
		System.out.println(text);
		System.out.println("-----------------------------------------");

	}

	// TODO ЗАГЛУШКА получение всех preDeal
	private List<PreDeal> getAllPreDeal() {

		List<PreDeal> preDeals = new ArrayList<>();
		PreDeal preDeal = new PreDeal();
		preDeal.setId(1);
		preDeal.setSellerHashCode("fgfag65jgjgs7fah85d6");
		preDeal.setCustomerHashCode("hfkhfhghd6hsdhf457");
		preDeal.setTenderId(3);
		preDeal.setLotId(2);
		preDeals.add(preDeal);

		PreDeal preDeal2 = new PreDeal();
		preDeal2.setId(2);
		preDeal2.setSellerHashCode("h2dfj63bsf3ydke7dgci65");
		preDeal2.setCustomerHashCode("t5dkk33fs6628dnbckj9");
		preDeal2.setTenderId(2);
		preDeal2.setLotId(3);
		preDeals.add(preDeal2);

		return preDeals;
	}
}
