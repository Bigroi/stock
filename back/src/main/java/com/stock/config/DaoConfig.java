package com.stock.config;

import com.stock.dao.*;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {

    @Value("${data.db.server}")
    private String server;

    @Value("${data.db.port}")
    private int port;

    @Value("${data.db.database}")
    private String database;

    @Value("${data.db.user}")
    private String username;

    @Value("${data.db.pass}")
    private String password;

    @Bean()
    public DataSource createDataSource() {
        var dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{server});
        dataSource.setPortNumbers(new int[]{port});
        dataSource.setDatabaseName(database);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public Jdbi createJdbi(DataSource dataSource) {
        var jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new Jackson2Plugin());
        return jdbi;
    }

    @Bean
    public Transactional createTransactional(Jdbi jdbi) {
        return new Transactional(jdbi);
    }

    @Bean
    public UserDao createUserDao(Jdbi jdbi) {
        return jdbi.onDemand(UserDao.class);
    }

    @Bean
    public CompanyDao createCompanyDao(Jdbi jdbi) {
        return jdbi.onDemand(CompanyDao.class);
    }

    @Bean
    public AddressDao createAddressDao(Jdbi jdbi) {
        return jdbi.onDemand(AddressDao.class);
    }

    @Bean
    public LabelDao createLabelDao(Jdbi jdbi) {
        return jdbi.onDemand(LabelDao.class);
    }

    @Bean
    public LotDao createLotDao(Jdbi jdbi) {
        return jdbi.onDemand(LotDao.class);
    }

    @Bean
    public TenderDao createTenderDao(Jdbi jdbi) {
        return jdbi.onDemand(TenderDao.class);
    }

    @Bean
    public ProductDao createProductDao(Jdbi jdbi) {
        return jdbi.onDemand(ProductDao.class);
    }

    @Bean
    public ProductCategoryDao createProductCategoryDao(Jdbi jdbi) {
        return jdbi.onDemand(ProductCategoryDao.class);
    }

    @Bean
    public DealDao createDealDao(Jdbi jdbi) {
        return jdbi.onDemand(DealDao.class);
    }

    @Bean
    public BidBlackListDao createBidBlackListDao(Jdbi jdbi) {
        return jdbi.onDemand(BidBlackListDao.class);
    }

    @Bean
    public UserCommentDao createUserCommentDao(Jdbi jdbi) {
        return jdbi.onDemand(UserCommentDao.class);
    }

    @Bean
    public TelegramBotDao createTelegramBotDao(Jdbi jdbi) {
        return jdbi.onDemand(TelegramBotDao.class);
    }
}
