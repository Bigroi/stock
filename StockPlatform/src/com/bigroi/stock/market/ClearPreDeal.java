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
	private final static String EXPIRED_LINK_FILE = "expiredLinkUTF8.txt";
	private final static String EXPIRED_LINK_OPPONENT_FILE = "expiredLinkOpponentUTF8.txt";

	@Override
	public void run() {
		// С учетом того, что в preDeal не храняться пары YY 	
		// too hard to read!
		try {			
			Map<MessagePart, String> expiredLinkMessage = MessageFromFile.read(EXPIRED_LINK_FILE);
			Map<MessagePart, String> expiredLinkOpponentMessage = MessageFromFile.read(EXPIRED_LINK_OPPONENT_FILE);
			List<PreDeal> predials = DaoFactory.getPreDealDao().getAllPreDeal();
			String expiredSubject = expiredLinkMessage.get(MessagePart.SUBJECT);
			String expiredOpponentSubject = expiredLinkOpponentMessage.get(MessagePart.SUBJECT);
			for (PreDeal preDeal : predials) {
				Lot lot = DaoFactory.getLotDao().getById(preDeal.getLotId());
				String sellerEmail = DaoFactory.getCompanyDao().getById(lot.getSellerId()).getEmail();	
				
				Tender tender = DaoFactory.getTenderDao().getById(preDeal.getTenderId());
				String customerEmail = DaoFactory.getCompanyDao().getById(tender.getCustomerId()).getEmail();	
				
				lot.setStatus(Status.IN_GAME);
				//убрать sysout, но параметры оставить				
				System.out.println(DaoFactory.getLotDao().updateById(lot));		
				//убрать sysout, но параметры оставить	
				tender.setStatus(Status.IN_GAME);
				System.out.println(DaoFactory.getTenderDao().updateById(tender));	
				
				String expiredText = expiredLinkMessage.get(MessagePart.TEXT);
				String expiredOpponentText = expiredLinkOpponentMessage.get(MessagePart.TEXT);	
				
				//убрать sysout
				System.out.println(preDeal.getId() + " Seller Approv - " + preDeal.getSellerApprovBool());
				System.out.println(preDeal.getId() + " Cust Approv - " + preDeal.getCustApprovBool());

				 
				if ( preDeal.getSellerApprovBool()){							
					MessagerFactory.getMailManager().send(sellerEmail, expiredOpponentSubject, replaceText(expiredOpponentText, preDeal.getLotId()));					
				}else {					
					MessagerFactory.getMailManager().send(sellerEmail, expiredSubject, replaceText(expiredText, preDeal.getLotId()));					
				}		
				if (preDeal.getCustApprovBool()){				
					MessagerFactory.getMailManager().send(customerEmail, expiredOpponentSubject, replaceText(expiredOpponentText, preDeal.getTenderId()));
				}else {						
					MessagerFactory.getMailManager().send(customerEmail, expiredSubject, replaceText(expiredText, preDeal.getTenderId()));
				}								
			}
			DaoFactory.getPreDealDao().deleteAll();
		} catch (DaoException | IOException | MailManagerException e) {
			//TODO Emailing Admin
			e.printStackTrace();
		}
	}	

	private String replaceText(String text, long id) {
		return text.replaceAll("@id", id + "");
	}



	//	 TODO ЗАГЛУШКА для messengera
//	 private void sendEmail(String email, String subject, String text) {
//	 System.out.println("To: " + email);
//	 System.out.println("Subject: " + subject);
//	 System.out.println("Text: ");
//	 System.out.println(text);
//	 System.out.println("-----------------------------------------");
//	
//	 }

}
