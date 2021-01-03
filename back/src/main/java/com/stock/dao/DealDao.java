package com.stock.dao;

import com.stock.entity.PartnerChoice;
import com.stock.entity.business.DealRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    void create(@BindBean List<DealRecord> deals);

    @SqlQuery("SELECT * FROM DEAL WHERE SELLER_COMPANY_ID = :companyId OR BUYER_COMPANY_ID = :companyId")
    List<DealRecord> getByCompanyId(@Bind("companyId") UUID companyId);

    @SqlQuery("SELECT * FROM DEAL " +
            "WHERE ID = :id AND (SELLER_COMPANY_ID = :companyId OR BUYER_COMPANY_ID = :companyId)")
    Optional<DealRecord> getByIdAndCompanyId(@Bind("id") UUID id, @Bind("companyId") UUID companyId);

    @SqlUpdate("UPDATE DEAL " +
            "SET SELLER_CHOICE = :choice " +
            "WHERE ID = :id AND SELLER_COMPANY_ID = :companyId AND SELLER_CHOICE = 'ON_APPROVE'")
    boolean changeSellerChoice(
            @Bind("id") UUID id,
            @Bind("companyId") UUID companyId,
            @Bind("choice") PartnerChoice choice
    );

    @SqlUpdate("UPDATE DEAL " +
            "SET BUYER_CHOICE = :choice " +
            "WHERE ID = :id AND BUYER_COMPANY_ID = :companyId AND BUYER_CHOICE = 'ON_APPROVE'")
    boolean changeBuyerChoice(
            @Bind("id") UUID id,
            @Bind("companyId") UUID companyId,
            @Bind("choice") PartnerChoice choice
    );

    @SqlUpdate("UPDATE DEAL " +
            "SET BUYER_ALERT = FALSE " +
            "WHERE BUYER_COMPANY_ID = :companyId")
    void resetAlertsForSeller(@Bind("companyId") UUID companyId);

    @SqlUpdate("UPDATE DEAL " +
            "SET SELLER_ALERT = FALSE " +
            "WHERE SELLER_COMPANY_ID = :companyId")
    void resetAlertsForBuyer(@Bind("companyId") UUID companyId);
}
