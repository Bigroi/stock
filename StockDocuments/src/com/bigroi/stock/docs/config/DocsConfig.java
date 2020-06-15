package com.bigroi.stock.docs.config;

import com.bigroi.stock.docs.DocumentGenerator;
import com.bigroi.stock.docs.impl.DocumentGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DocsConfig {

    @Bean
    public DocumentGenerator createDocumentGenerator() {
        return new DocumentGeneratorImpl(Arrays.asList("Deal-en_US.doc", "Deal-ru_RU.doc", "Deal-pl.doc"));
    }

}
