package com.bigroi.stock.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {
	private static final ApplicationContext CONTEX = new ClassPathXmlApplicationContext("spring.xml");

	public static UserDao getUserDao() {
		return (UserDao) CONTEX.getBean("userDao");
	}

	public static CompanyDao getCompanyDao() {
		return (CompanyDao) CONTEX.getBean("companyDao");
	}

	public static ProductDao getProductDao() {
		return (ProductDao) CONTEX.getBean("productDao");
	}

	public static LotDao getLotDao() {
		return (LotDao) CONTEX.getBean("lotDao");
	}
	
	public static TenderDao getTenderDao(){
		return (TenderDao) CONTEX.getBean("tenderDao");
	}
	
	public static ArchiveDao getArchiveDao(){
		return (ArchiveDao) CONTEX.getBean("archiveDao");
	}
	
	public static BlacklistDao getBlacklistDao(){
		return (BlacklistDao) CONTEX.getBean("blacklistDao");
	}

	public static ApplicationContext getContext() {
		return CONTEX;
	}

	public static StubDao getStubDao(Class<?> clazz) {
		return new StubDao(clazz);
	}

}
