package com.bigroi.stock.trade;

import static com.bigroi.stock.bean.common.YesNoSingle.Y;

import java.util.List;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class ClearPreDial implements Runnable {

	@Override
	public void run() {
		try {
			List<PreDeal> predials = DaoFactory.getPreDealDao().getAllPreDeal();
			for (PreDeal preDeal : predials) {
				if (Y.name().equals(preDeal.getSellerApprov())){
					
				}
			}
		} catch (DaoException e) {			
			e.printStackTrace();
		} 
		
		

	}
	
	// TODO «¿√À”ÿ ¿ ‰Îˇ messengera
//		private void sendEmail(String email, String subject, String text) {
//			System.out.println("To: " + email);
//			System.out.println("Subject: " + subject);
//			System.out.println("Text: ");
//			System.out.println(text);
//			System.out.println("-----------------------------------------");
//
//		}	

}
