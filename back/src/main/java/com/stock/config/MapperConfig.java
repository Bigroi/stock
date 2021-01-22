package com.stock.config;

import com.stock.mapper.DealMapper;
import com.stock.mapper.impl.DealMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public DealMapper createDealMapper() {
        return new DealMapperImpl();
    }

}
