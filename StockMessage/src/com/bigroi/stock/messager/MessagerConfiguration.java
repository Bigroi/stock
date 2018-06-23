package com.bigroi.stock.messager;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.bigroi.stock.messager.message.CustomerCanceledMessage;
import com.bigroi.stock.messager.message.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.DealExparationMessageForCustomer;
import com.bigroi.stock.messager.message.DealExparationMessageForSeller;
import com.bigroi.stock.messager.message.FeedBackMessage;
import com.bigroi.stock.messager.message.LinkResetPasswordMessage;
import com.bigroi.stock.messager.message.LotExparationMessage;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.messager.message.ResetUserPasswordMessage;
import com.bigroi.stock.messager.message.SellerCanceledMessage;
import com.bigroi.stock.messager.message.SuccessDealMessageForCustomer;
import com.bigroi.stock.messager.message.SuccessDealMessageForSeller;
import com.bigroi.stock.messager.message.TenderExparationMessage;

@Configuration
@PropertySource("classpath:mail.properties")
public class MessagerConfiguration {

	@Value("${mail.smtp.auth}")
	private String auth;
	@Value("${mail.smtp.starttls.enable}")
	private String starttls;
	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;
	@Value("${mail.mime.charset}")
	private String charset;
	
	@Value("${mail.user}")
	private String user;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.adminUser}")
	private String adminUser;
	@Value("${mail.serverAdress}")
	private String serverAdress;
	
	@Bean
	public MailManager getMailManager(){
		MailManagerImpl impl = new MailManagerImpl();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", starttls);
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.mime.charset", charset);
		
		impl.setMailProperties(properties);
		impl.setAdminUser(adminUser);
		impl.setPassword(password);
		impl.setServerAdress(serverAdress);
		impl.setUser(adminUser);
		
		return impl;
	}
	
	@Bean
	public CustomerCanceledMessage getCustomerCanceledMessage() throws MessageException{
		return new CustomerCanceledMessage("dealCancelled.txt");
	}
	
	@Bean
	public DealConfirmationMessageForCustomer getDealConfirmationMessageForCustomer() throws MessageException{
		return new DealConfirmationMessageForCustomer("confirmationForCustomer.txt");
	}

	@Bean
	public DealConfirmationMessageForSeller getDealConfirmationMessageForSeller() throws MessageException{
		return new DealConfirmationMessageForSeller("confirmationForSeller.txt");
	}

	@Bean
	public ResetUserPasswordMessage getResetUserPasswordMessage() throws MessageException{
		return new ResetUserPasswordMessage("passwordReset.txt");
	}
	
	@Bean
	public SellerCanceledMessage getSellerCanceledMessage() throws MessageException{
		return new SellerCanceledMessage("dealCancelled.txt");
	}
	
	@Bean
	public SuccessDealMessageForCustomer getSuccessDealMessageForCustomer() throws MessageException{
		return new SuccessDealMessageForCustomer("successDeal.txt");
	}
	
	@Bean
	public SuccessDealMessageForSeller getSuccessDealMessageForSeller() throws MessageException{
		return new SuccessDealMessageForSeller("successDeal.txt");
	}

	@Bean("dealExparationMessageForCustomer")
	public DealExparationMessageForCustomer getDealExparationMessageForCustomer() throws MessageException{
		return new DealExparationMessageForCustomer("expiredDeal.txt");
	}
	
	@Bean("dealExparationMessageForCustomerByOpponent")
	public DealExparationMessageForCustomer getDealExparationMessageForCustomerByOpponent() throws MessageException{
		return new DealExparationMessageForCustomer("expiredDealOpponent.txt");
	}

	@Bean("dealExparationMessageForSeller")
	public DealExparationMessageForSeller getDealExparationMessageForSeller() throws MessageException{
		return new DealExparationMessageForSeller("expiredDeal.txt");
	}

	@Bean("dealExparationMessageForSellerByOpponent")
	public DealExparationMessageForSeller getDealExparationMessageForSellerByOpponent() throws MessageException{
		return new DealExparationMessageForSeller("expiredDealOpponent.txt");
	}

	@Bean
	public LotExparationMessage getLotExparationMessage() throws MessageException{
		return new LotExparationMessage("lotExparied.txt");
	}

	@Bean
	public TenderExparationMessage getTenderExparationMessage() throws MessageException{
		return new TenderExparationMessage("tenderExparied.txt");
	}
	
	@Bean
	public LinkResetPasswordMessage getLinkResetPasswordMessage() throws MessageException{
		return new LinkResetPasswordMessage("linkResetPassw.txt");
	}
	
	@Bean
	public FeedBackMessage getFeedBackMessage() throws MessageException{
		return new FeedBackMessage(adminUser);
	}
}