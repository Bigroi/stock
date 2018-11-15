package com.bigroi.stock.messager;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.bigroi.stock.messager.message.FeedBackMessage;
import com.bigroi.stock.messager.message.LinkResetPasswordMessage;
import com.bigroi.stock.messager.message.LotExparationMessage;
import com.bigroi.stock.messager.message.ResetUserPasswordMessage;
import com.bigroi.stock.messager.message.TenderExparationMessage;
import com.bigroi.stock.messager.message.deal.BuyerCanceledMessage;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForBuyer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForBuyer;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForSeller;
import com.bigroi.stock.messager.message.deal.SellerCanceledMessage;
import com.bigroi.stock.messager.message.deal.SuccessDealMessageForBuyer;
import com.bigroi.stock.messager.message.deal.SuccessDealMessageForSeller;

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
	public BuyerCanceledMessage getBuyerCanceledMessage(){
		return new BuyerCanceledMessage("dealCancelled", "txt");
	}
	
	@Bean
	public DealConfirmationMessageForBuyer getDealConfirmationMessageForBuyer(){
		return new DealConfirmationMessageForBuyer("confirmationForBuyer", "txt");
	}

	@Bean
	public DealConfirmationMessageForSeller getDealConfirmationMessageForSeller(){
		return new DealConfirmationMessageForSeller("confirmationForSeller", "txt");
	}

	@Bean
	public ResetUserPasswordMessage getResetUserPasswordMessage(){
		return new ResetUserPasswordMessage("passwordReset", "txt");
	}
	
	@Bean
	public SellerCanceledMessage getSellerCanceledMessage(){
		return new SellerCanceledMessage("dealCancelled", "txt");
	}
	
	@Bean
	public SuccessDealMessageForBuyer getSuccessDealMessageForBuyer(){
		return new SuccessDealMessageForBuyer("successDeal", "txt");
	}
	
	@Bean
	public SuccessDealMessageForSeller getSuccessDealMessageForSeller(){
		return new SuccessDealMessageForSeller("successDeal", "txt");
	}

	@Bean("dealExparationMessageForBuyer")
	public DealExparationMessageForBuyer getDealExparationMessageForBuyer(){
		return new DealExparationMessageForBuyer("expiredDeal", "txt");
	}
	
	@Bean("dealExparationMessageForBuyerByOpponent")
	public DealExparationMessageForBuyer getDealExparationMessageForBuyerByOpponent(){
		return new DealExparationMessageForBuyer("expiredDealOpponent", "txt");
	}

	@Bean("dealExparationMessageForSeller")
	public DealExparationMessageForSeller getDealExparationMessageForSeller(){
		return new DealExparationMessageForSeller("expiredDeal", "txt");
	}

	@Bean("dealExparationMessageForSellerByOpponent")
	public DealExparationMessageForSeller getDealExparationMessageForSellerByOpponent(){
		return new DealExparationMessageForSeller("expiredDealOpponent", "txt");
	}

	@Bean
	public LotExparationMessage getLotExparationMessage(){
		return new LotExparationMessage("lotExparied", "txt");
	}

	@Bean
	public TenderExparationMessage getTenderExparationMessage(){
		return new TenderExparationMessage("tenderExparied", "txt");
	}
	
	@Bean
	public LinkResetPasswordMessage getLinkResetPasswordMessage(){
		return new LinkResetPasswordMessage("linkResetPassw", "txt");
	}
	
	@Bean
	public FeedBackMessage getFeedBackMessage(){
		return new FeedBackMessage(user);
	}
}
