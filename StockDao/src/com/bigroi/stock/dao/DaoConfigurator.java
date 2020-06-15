package com.bigroi.stock.dao;

import com.bigroi.stock.dao.db.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DaoConfigurator {

    @Value("${data.db.url}")
    private String url;

    @Value("${data.db.name}")
    private String username;

    private final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

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
        properties.put("verifyServerCertificate", "false");
        properties.put("useSSL", "false");
        dataSource.setConnectionProperties(properties);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        return dataSource;
    }

    @Bean
    public AddressDao createAddressDao(DataSource datasource) {
        return new AddressDaoImpl(datasource);
    }

    @Bean
    public BlacklistDao createBlacklistDao(DataSource datasource) {
        return new BlacklistDaoImpl(datasource);
    }

    @Bean
    public CompanyDao createCompanyDao(DataSource datasource) {
        return new CompanyDaoImpl(datasource);
    }

    @Bean
    public DealDao createDealDao(DataSource datasource) {
        return new DealDaoImpl(datasource);
    }

    @Bean
    public EmailDao createEmailDao(DataSource datasource) {
        return new EmailDaoImpl(datasource);
    }

    @Bean
    public GenerateKeyDao createGenerateKeyDao(DataSource datasource) {
        return new GenerateKeyDaoImpl(datasource);
    }

    @Bean
    public LabelDao createLabelDao(DataSource datasource) {
        return new LabelDaoImpl(datasource);
    }

    @Bean
    public LotDao createLotDao(DataSource datasource) {
        return new LotDaoImpl(datasource);
    }

    @Bean
    public ProductCategoryDao createProductCategoryDao(DataSource datasource) {
        return new ProductCategoryDaoImpl(datasource);
    }

    @Bean
    public ProductDao createProductDao(DataSource datasource) {
        return new ProductDaoImpl(datasource);
    }

    @Bean
    public TenderDao createTenderDao(DataSource datasource) {
        return new TenderDaoImpl(datasource);
    }

    @Bean
    public UserCommentDao createUserCommentDao(DataSource datasource) {
        return new UserCommentDaoImpl(datasource);
    }

    @Bean
    public UserDao createUserDao(DataSource datasource) {
        return new UserDaoImpl(datasource);
    }

    @Bean
    public UserRoleDao createUserRoleDao(DataSource datasource) {
        return new UserRoleDaoImpl(datasource);
    }
}
