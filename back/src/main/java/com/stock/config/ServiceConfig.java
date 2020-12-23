package com.stock.config;

import com.stock.client.email.EmailClient;
import com.stock.dao.*;
import com.stock.entity.business.LotRecord;
import com.stock.entity.business.TenderRecord;
import com.stock.security.JwtTokenProcessor;
import com.stock.service.*;
import com.stock.service.impl.*;
import com.stock.service.transactional.AddressServiceTransactional;
import com.stock.service.transactional.BidServiceTransactional;
import com.stock.service.transactional.UserServiceTransactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public ProductService createProductService(ProductDao productDao) {
        return new ProductServiceImpl(productDao);
    }

}
