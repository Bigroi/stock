package com.bigroi.stock.messager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.messager.message.Message;

@SuppressWarnings("unchecked")
public class MessagerFactory {

	private static ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("spring-message.xml");
	
	public static MailManager getMailManager(){
		return (MailManager) CONTEXT.getBean("mailManager");
	}
	
	public static Message<Deal> getCustomerCanceledMessage(){
		return (Message<Deal>) CONTEXT.getBean("customerCanceledMessage");
	}
	
	public static Message<Deal> getDealConfirmationMessageForCustomer(){
		return (Message<Deal>) CONTEXT.getBean("dealConfirmationMessageForCustomer");
	}

	public static Message<Deal> getDealConfirmationMessageForSeller(){
		return (Message<Deal>) CONTEXT.getBean("dealConfirmationMessageForSeller");
	}

	public static Message<StockUser> getResetUserPasswordMessage(){
		return (Message<StockUser>) CONTEXT.getBean("resetUserPasswordMessage");
	}
	
	public static Message<Deal> getSellerCanceledMessage(){
		return (Message<Deal>) CONTEXT.getBean("sellerCanceledMessage");
	}
	
	public static Message<Deal> getSuccessDealMessageForCustomer(){
		return (Message<Deal>) CONTEXT.getBean("successDealMessageForCustomer");
	}
	
	public static Message<Deal> getSuccessDealMessageForSeller(){
		return (Message<Deal>) CONTEXT.getBean("successDealMessageForSeller");
	}

	public static Message<Deal> getDealExparationMessageForCustomer(){
		return (Message<Deal>) CONTEXT.getBean("dealExparationMessageForCustomer");
	}
	
	public static Message<Deal> getDealExparationMessageForCustomerByOpponent(){
		return (Message<Deal>) CONTEXT.getBean("dealExparationMessageForCustomerByOpponent");
	}

	public static Message<Deal> getDealExparationMessageForSeller(){
		return (Message<Deal>) CONTEXT.getBean("dealExparationMessageForSeller");
	}

	public static Message<Deal> getDealExparationMessageForSellerByOpponent(){
		return (Message<Deal>) CONTEXT.getBean("dealExparationMessageForSellerByOpponent");
	}

	public static Message<Lot> getLotExparationMessage() {
		return (Message<Lot>) CONTEXT.getBean("lotExparationMessage");
	}

	public static Message<Tender> getTenderExparationMessage() {
		return (Message<Tender>) CONTEXT.getBean("tenderExparationMessage");
	}
	
	public static Message<InviteUser> getInviteExparationMessage(){
		return (Message<InviteUser>) CONTEXT.getBean("inviteExparationMessage");
	}
}
