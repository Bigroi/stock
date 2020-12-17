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
import com.stock.generator.impl.DocumentGeneratorImpl;
import com.stock.generator.impl.FreeMakerGeneratorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GeneratorConfig {

    @Value("${ses.from}")
    private String defaultFrom;

    @Value("${ses.accessKey}")
    private String accessKey;

    @Value("${ses.secretKey}")
    private String secretKey;

    @Value("${ses.staticTo:#{null}}")
    private String staticTo;

    @Bean
    public Generator createGenerator() {
        return new FreeMakerGeneratorImpl();
    }

    @Bean
    public DocumentGenerator createDocumentGenerator() {
        return new DocumentGeneratorImpl(Arrays.asList("docs/Deal-EN.doc", "docs/Deal-PL.doc", "docs/Deal-RU.doc"));
    }

}
