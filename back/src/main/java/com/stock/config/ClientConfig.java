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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Value("${ses.from}")
    private String defaultFrom;

    @Value("${ses.support}")
    private String support;

    @Value("${ses.accessKey}")
    private String accessKey;

    @Value("${ses.secretKey}")
    private String secretKey;

    @Value("${ses.staticTo:#{null}}")
    private String staticTo;

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
            DocumentGenerator documentGenerator
    ) {
        var manager = new SesEmailClient(innerClient, generator, defaultFrom, support, documentGenerator);
        if (staticTo == null) {
            return manager;
        } else {
            return new StaticEmailClient(manager, staticTo);
        }
    }

}
