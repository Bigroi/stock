package com.stock.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.stock.client.email.EmailClient;
import com.stock.client.email.SesEmailClient;
import com.stock.client.email.StaticEmailClient;
import com.stock.generator.DocumentGenerator;
import com.stock.generator.Generator;
import com.stock.mapper.DealMapper;
import com.stock.mapper.impl.DealMapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public DealMapper createDealMapper() {
        return new DealMapperImpl();
    }

}
