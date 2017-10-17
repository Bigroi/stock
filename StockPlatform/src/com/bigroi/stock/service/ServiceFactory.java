package com.bigroi.stock.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {

	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring-service.xml");
	
	public static UserService getUserService(){
		return (UserService) CONTEX.getBean("userService");
	}
	
	public static CompanyService getCompanyService(){
		return (CompanyService) CONTEX.getBean("companyService");
	}
	
	public static LotService getLotService(){
		return (LotService) CONTEX.getBean("lotService");
	}
	
	public static ProductService getProductService(){
		return (ProductService) CONTEX.getBean("productService");
	}
	
	public static TenderService getTenderService(){
		return (TenderService) CONTEX.getBean("tenderService");
	}
	
	public static ReferenceService getReferenceService(){
		return (ReferenceService) CONTEX.getBean("referenceService");
	}

	public static ApplicationContext getContext() {
		return  CONTEX;
		
	}
	
	
	
	
}
