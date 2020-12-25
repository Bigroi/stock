package com.stock.dao;

import com.stock.entity.business.DealRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.ArrayList;
import java.util.List;

@RegisterBeanMapper(DealRecord.class)
public interface DealDao {

    @SqlQuery("SELECT * FROM DEAL " +
            "WHERE (BUYER_CHOICE = 'ON_APPROVE' AND SELLER_CHOICE <> 'REJECTED') " +
            "   OR (SELLER_CHOICE = 'ON_APPROVE' AND BUYER_CHOICE <> 'REJECTED')")
    List<DealRecord> getOnApprove();

    @SqlUpdate("DELETE FROM DEAL " +
            "WHERE (BUYER_CHOICE = 'ON_APPROVE' AND SELLER_CHOICE <> 'REJECTED') " +
            "   OR (SELLER_CHOICE = 'ON_APPROVE' AND BUYER_CHOICE <> 'REJECTED')")
    void deleteOnApprove();

    @SqlBatch("INSERT INTO DEAL (ID, LOT_ID, TENDER_ID, SELLER_COMPANY_ID, BUYER_COMPANY_ID, SELLER_ADDRESS, " +
            "BUYER_ADDRESS, CREATION_DATE, SELLER_CHOICE, BUYER_CHOICE, PRICE, VOLUME, CATEGORY_ID, " +
            "PHOTO, SELLER_DESCRIPTION, BUYER_DESCRIPTION, PROCESSING, PACKAGING, SELLER_ALERT, BUYER_ALERT) " +
            "VALUES (:id, :lotId, :tenderId, :sellerCompanyId, :buyerCompanyId, CAST(:sellerAddress as json), " +
            "CAST(:buyerAddress as json), :creationDate, :sellerChoice, :buyerChoice, :price, :volume, :categoryId, " +
            ":photo, :sellerDescription, :buyerDescription, :processing, :packaging, :sellerAlert, :buyerAlert)")
    void create(List<DealRecord> deals);
}
