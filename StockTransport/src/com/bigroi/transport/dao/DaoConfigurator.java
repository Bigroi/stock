package com.bigroi.transport.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:datasourse.properties")
public class DaoConfigurator {

	@Value("${data.db.url}")
	private String url;
	
	@Value("${data.db.name}")
	private String username;
	
	private String driverClassName = "com.mysql.jdbc.Driver";

	@Value("${data.db.pass}")
	private String password;

	@Bean
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		return dataSource;
	}
}

