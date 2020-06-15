package com.bigroi.stock;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
public class SpringBootStarter implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class, args);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var text = new StringHttpMessageConverter();
        text.setSupportedMediaTypes(ImmutableList.of(new MediaType("text", "JSON", StandardCharsets.UTF_8)));
        converters.add(text);

        var bytes = new ByteArrayHttpMessageConverter();
        bytes.setSupportedMediaTypes(ImmutableList.of(new MediaType("image", "png")));
        converters.add(bytes);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        var viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        var config = new TilesConfigurer();
        config.setCheckRefresh(true);
        config.setDefinitions("tiles.xml");
        return config;
    }

}
