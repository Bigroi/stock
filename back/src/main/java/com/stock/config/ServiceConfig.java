package com.stock.config;

import com.stock.client.email.EmailClient;
import com.stock.dao.*;
import com.stock.security.JwtTokenProcessor;
import com.stock.service.FeedBackService;
import com.stock.service.LabelService;
import com.stock.service.UserService;
import com.stock.service.impl.FeedBackServiceImpl;
import com.stock.service.impl.LabelServiceImpl;
import com.stock.service.impl.UserServiceImpl;
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

}
