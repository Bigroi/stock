package com.bigroi.stock.messager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.messager.message.Message;

@SuppressWarnings("unchecked")
public class MessagerFactory {

	private static ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("spring-message.xml");
	
	public static MailManager getMailManager(){
		return (MailManager) CONTEXT.getBean("mailManager");
	}
	
	public static Link getLink(){
		return (Link) CONTEXT.getBean("link");
	}
	
	public static Message<PreDeal> getCustomerCanceledMessage(){
		return (Message<PreDeal>) CONTEXT.getBean("customerCanceledMessage");
	}
	
	public static Message<PreDeal> getDealConfirmationMessageForCustomer(){
		return (Message<PreDeal>) CONTEXT.getBean("dealConfirmationMessageForCustomer");
	}

	public static Message<PreDeal> getDealConfirmationMessageForSeller(){
		return (Message<PreDeal>) CONTEXT.getBean("dealConfirmationMessageForSeller");
	}

	public static Message<StockUser> getResetUserPasswordMessage(){
		return (Message<StockUser>) CONTEXT.getBean("resetUserPasswordMessage");
	}
	
	public static Message<PreDeal> getSellerCanceledMessage(){
		return (Message<PreDeal>) CONTEXT.getBean("sellerCanceledMessage");
	}
	
	public static Message<PreDeal> getSuccessDealMessageForCustomer(){
		return (Message<PreDeal>) CONTEXT.getBean("successDealMessageForCustomer");
	}
	
	public static Message<PreDeal> getSuccessDealMessageForSeller(){
		return (Message<PreDeal>) CONTEXT.getBean("successDealMessageForSeller");
	}

	public static Message<PreDeal> getDealExparationMessageForCustomer(){
		return (Message<PreDeal>) CONTEXT.getBean("dealExparationMessageForCustomer");
	}
	
	public static Message<PreDeal> getDealExparationMessageForCustomerByOpponent(){
		return (Message<PreDeal>) CONTEXT.getBean("dealExparationMessageForCustomerByOpponent");
	}

	public static Message<PreDeal> getDealExparationMessageForSeller(){
		return (Message<PreDeal>) CONTEXT.getBean("dealExparationMessageForSeller");
	}

	public static Message<PreDeal> getDealExparationMessageForSellerByOpponent(){
		return (Message<PreDeal>) CONTEXT.getBean("dealExparationMessageForSellerByOpponent");
	}

	public static Message<Lot> getLotExparationMessage() {
		return (Message<Lot>) CONTEXT.getBean("lotExparationMessage");
	}

	public static Message<Tender> getTenderExparationMessage() {
		return (Message<Tender>) CONTEXT.getBean("tenderExparationMessage");
	}
}
