package com.stock.config;

import com.stock.generator.DocumentGenerator;
import com.stock.generator.Generator;
import com.stock.generator.impl.DocumentGeneratorImpl;
import com.stock.generator.impl.FreeMakerGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GeneratorConfig {

    @Bean
    public Generator createGenerator() {
        return new FreeMakerGeneratorImpl();
    }

    @Bean
    public DocumentGenerator createDocumentGenerator() {
        return new DocumentGeneratorImpl(Arrays.asList("docs/Deal-EN.doc", "docs/Deal-PL.doc", "docs/Deal-RU.doc"));
    }

}
