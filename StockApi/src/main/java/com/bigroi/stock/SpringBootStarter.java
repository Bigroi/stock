package com.bigroi.stock;

import java.nio.charset.Charset;
import java.util.List;

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

import com.google.common.collect.ImmutableList;

@SpringBootApplication
public class SpringBootStarter implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStarter.class, args);
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		var text = new StringHttpMessageConverter();
		text.setSupportedMediaTypes(ImmutableList.of(new MediaType("text", "JSON", Charset.forName("UTF-8"))));
		converters.add(text);
		
		var bytes = new ByteArrayHttpMessageConverter();
		bytes.setSupportedMediaTypes(ImmutableList.of(new MediaType("image", "png")));
		converters.add(bytes);
	}

	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
    }
	
	@Bean
    public TilesConfigurer tilesConfigurer() {
        var config =  new TilesConfigurer();
        config.setCheckRefresh(true);
        return config;
    }

}
