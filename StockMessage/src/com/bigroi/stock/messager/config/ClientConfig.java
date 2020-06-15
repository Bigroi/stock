package com.bigroi.stock.messager.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.docs.DocumentGenerator;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.messager.email.SesEmailClient;
import com.bigroi.stock.messager.email.StaticEmailClient;
import com.bigroi.stock.messager.generator.Generator;
import com.bigroi.stock.messager.generator.impl.FreeMakerGeneratorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Value("${email.ses.from}")
    private String defaultFrom;

    @Value("${email.ses.accessKey}")
    private String accessKey;

    @Value("${email.ses.secretKey}")
    private String secretKey;

    @Value("${email.ses.staticTo:#{null}}")
    private String staticTo;

    @Value("${email.ses.support}")
    private String support;

    @Bean
    public AmazonSimpleEmailService createEmailInnerClient() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean
    public EmailClient createEmailClient(
            AmazonSimpleEmailService innerClient,
            Generator generator,
            EmailDao emailDao,
            DocumentGenerator documentGenerator
    ) {
        var manager = new SesEmailClient(innerClient, generator, defaultFrom, support, emailDao, documentGenerator);
        if (staticTo == null) {
            return manager;
        } else {
            return new StaticEmailClient(manager, staticTo);
        }
    }

    @Bean
    public Generator createGenerator() {
        return new FreeMakerGeneratorImpl();
    }

}
