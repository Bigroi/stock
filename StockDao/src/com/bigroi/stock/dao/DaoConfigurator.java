package com.bigroi.stock.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement
public class DaoConfigurator {

    @Value("${data.db.url}")
    private String url;

    @Value("${data.db.name}")
    private String username;

    private String driverClassName = "com.mysql.jdbc.Driver";

    @Value("${data.db.pass}")
    private String password;

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = new Properties();
        properties.put("allowMultiQuerie", "true");
        dataSource.setConnectionProperties(properties);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}
