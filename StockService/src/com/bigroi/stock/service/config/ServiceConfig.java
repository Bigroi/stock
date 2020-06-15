package com.bigroi.stock.service.config;

import com.bigroi.stock.dao.*;
import com.bigroi.stock.docs.DocumentGenerator;
import com.bigroi.stock.docs.impl.DocumentGeneratorImpl;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.service.*;
import com.bigroi.stock.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class ServiceConfig {

    @Bean
    public AddressService createAddressService(AddressDao addressDao) {
        return new AddressServiceImpl(addressDao);
    }

    @Bean
    public CompanyService createCompanyService(LotDao lotDao, TenderDao tenderDao, CompanyDao companyDao) {
        return new CompanyServiceImpl(lotDao, tenderDao, companyDao);
    }

    @Bean
    public DealService createDealService(
            BlacklistDao blacklistDao,
            DealDao dealDao,
            LotDao lotDao,
            TenderDao tenderDao,
            EmailClient emailClient,
            LabelDao labelDao
    ) {
        return new DealServiceImpl(blacklistDao, dealDao, lotDao, tenderDao, emailClient, labelDao);
    }

    @Bean
    public LabelService createLabelService(LabelDao labelDao) {
        return new LabelServiceImpl(labelDao);
    }

    @Bean
    public LotService createLotService(ProductDao productDao, LotDao lotDao) {
        return new LotServiceImpl(30000, productDao, lotDao);
    }

    @Bean
    public MarketService createMarketService(
            LotDao lotDao,
            TenderDao tenderDao,
            DealDao dealDao,
            CompanyDao companyDao,
            LabelDao labelDao,
            EmailClient emailClient
    ) {
        return new MarketServiceImpl(lotDao, tenderDao, dealDao, companyDao, labelDao, emailClient);
    }

    @Bean
    public ProductCategoryService createProductCategoryService(ProductCategoryDao productCategoryDao) {
        return new ProductCategoryServiceImpl(productCategoryDao);
    }

    @Bean
    public ProductService createProductService(ProductDao productDao, LotDao lotDao, TenderDao tenderDao) {
        return new ProductServiceImpl(productDao, lotDao, tenderDao);
    }

    @Bean
    public TenderService createTenderService(ProductDao productDao, TenderDao tenderDao) {
        return new TenderServiceImpl(30000, productDao, tenderDao);
    }

    @Bean
    public TradeService createTradeService(
            ProductDao productDao,
            DealDao dealDao,
            LotDao lotDao,
            TenderDao tenderDao,
            AddressDao addressDao,
            EmailClient emailClient,
            LabelDao labelDao
    ) {
        return new TradeServiceImpl(productDao, dealDao, lotDao, tenderDao, addressDao, emailClient, labelDao);
    }

    @Bean
    public UserCommentService createUserCommentService(UserCommentDao userCommentDao) {
        return new UserCommentServiceImpl(userCommentDao);
    }

    @Bean
    public UserService createUserService(
            UserDao userDao,
            CompanyDao companyDao,
            UserRoleDao userRoleDao,
            GenerateKeyDao keysDao,
            AddressDao addressDao,
            EmailClient emailClient,
            PasswordEncoder passwordEncoder
    ) {
        return new UserServiceImpl(userDao, companyDao, userRoleDao, keysDao, addressDao, emailClient, passwordEncoder);
    }

    @Bean
    public MessageService createMessageService(EmailDao emailDao, EmailClient emailClient) {
        return new MessageServiceImpl(emailDao, emailClient);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AlertService createAlertService(LotDao lotDao, TenderDao tenderDao, DealDao dealDao) {
        return new AlertServiceImpl(lotDao, tenderDao, dealDao);
    }
}
