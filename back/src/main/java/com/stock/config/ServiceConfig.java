package com.stock.config;

import com.stock.client.email.EmailClient;
import com.stock.dao.*;
import com.stock.entity.business.LotRecord;
import com.stock.entity.business.TenderRecord;
import com.stock.security.JwtTokenProcessor;
import com.stock.service.*;
import com.stock.service.impl.*;
import com.stock.service.transactional.*;
import com.stock.trading.TradeSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public UserService createUserService(
            UserDao userDao,
            CompanyDao companyDao,
            AddressDao addressDao,
            JwtTokenProcessor tokenProcessor,
            PasswordEncoder passwordEncoder,
            EmailClient emailClient,
            Transactional transactional
    ) {
        var service = new UserServiceImpl(
                userDao,
                companyDao,
                addressDao,
                tokenProcessor,
                passwordEncoder,
                emailClient
        );
        return new UserServiceTransactional(transactional, service);
    }

    @Bean
    public LabelService createLabelService(LabelDao labelDao) {
        return new LabelServiceImpl(labelDao);
    }

    @Bean
    public FeedBackService createFeedBackService(EmailClient emailClient) {
        return new FeedBackServiceImpl(emailClient);
    }

    @Bean
    public BidService<LotRecord> createLotService(LotDao lotDao, Transactional transactional) {
        var service = new LotServiceImpl(lotDao);
        return new BidServiceTransactional<>(transactional, service);
    }

    @Bean
    public BidService<TenderRecord> createTenderService(TenderDao tenderDao, Transactional transactional) {
        var service = new TenderServiceImpl(tenderDao);
        return new BidServiceTransactional<>(transactional, service);
    }

    @Bean
    public AddressService createAddressService(AddressDao addressDao, Transactional transactional) {
        var service = new AddressServiceImpl(addressDao);
        return new AddressServiceTransactional(transactional, service);
    }

    @Bean
    public ProductService createProductService(
            ProductDao productDao,
            LotDao lotDao,
            TenderDao tenderDao,
            ProductCategoryDao productCategoryDao,
            Transactional transactional
    ) {
        var service = new ProductServiceImpl(productDao, lotDao, tenderDao, productCategoryDao);
        return new ProductServiceTransactional(transactional, service);
    }

    @Bean
    public CategoryService createCategoryService(
            ProductCategoryDao productCategoryDao,
            ProductDao productDao,
            LotDao lotDao,
            TenderDao tenderDao,
            Transactional transactional
    ) {
        var service = new CategoryServiceImpl(productCategoryDao, productDao, lotDao, tenderDao);
        return new CategoryServiceTransactional(transactional, service);
    }

    @Bean
    public CompanyService createCompanyService(
            CompanyDao companyDao,
            LotDao lotDao,
            TenderDao tenderDao,
            Transactional transactional
    ) {
        var service = new CompanyServiceImpl(companyDao, lotDao, tenderDao);
        return new CompanyServiceTransactional(transactional, service);
    }

    @Bean
    public TradeService createTradeService(
            UserDao userDao,
            LotDao lotDao,
            TenderDao tenderDao,
            DealDao dealDao,
            LabelDao labelDao,
            ProductDao productDao,
            EmailClient emailClient,
            TradeSession tradeSession,
            Transactional transactional
    ) {
        var service = new TradeServiceImpl(
                userDao,
                lotDao,
                tenderDao,
                dealDao,
                labelDao,
                productDao,
                emailClient,
                tradeSession
        );
        return new TradeServiceTransactional(transactional, service);
    }

    @Bean()
    public TradeSession createTradeSession(
            DealDao dealDao,
            LotDao lotDao,
            TenderDao tenderDao,
            LabelDao labelDao,
            BidBlackListDao bidBlackListDao,
            UserDao userDao,
            EmailClient emailClient
    ) {
        return new TradeSession(dealDao, lotDao, tenderDao, labelDao, bidBlackListDao, userDao, emailClient);
    }
}
