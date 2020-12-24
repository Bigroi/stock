package com.stock.dao;

import com.stock.entity.BidStatus;
import com.stock.entity.business.TenderRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(TenderRecord.class)
public interface TenderDao {

    @SqlQuery("SELECT * FROM TENDER WHERE COMPANY_ID = :companyId")
    List<TenderRecord> getByCompanyId(@Bind("companyId") UUID companyId);

    @SqlQuery("SELECT * FROM TENDER WHERE COMPANY_ID = :companyId AND ID = :id")
    Optional<TenderRecord> getByIdAndCompanyId(@Bind("id") UUID id, @Bind("companyId") UUID companyId);

    @SqlUpdate("DELETE FROM TENDER WHERE COMPANY_ID = :companyId AND ID = :id")
    boolean deleteByIdAndCompanyId(@Bind("id") UUID id, @Bind("companyId") UUID companyId);

    @SqlUpdate("INSERT INTO TENDER (ID, DESCRIPTION, PRICE, MIN_VOLUME, MAX_VOLUME, COMPANY_ID, STATUS, CREATION_DATE, " +
            "EXPIRATION_DATE, ADDRESS_ID, PACKAGING, PROCESSING, DISTANCE, CATEGORY_ID, ALERT, PRODUCT_ID) " +
            "VALUES (:id, :description, :price, :minVolume, :maxVolume, :companyId, :status, :creationDate, " +
            ":expirationDate, :addressId, :packaging, :processing, :distance, :categoryId, :alert, :productId)")
    void create(@BindBean TenderRecord lot);

    @SqlUpdate("UPDATE TENDER " +
            "SET DESCRIPTION = :description, PRICE = :price, MIN_VOLUME = :minVolume, MAX_VOLUME = :maxVolume, " +
            "STATUS = :status, EXPIRATION_DATE = :expirationDate, ADDRESS_ID = :addressId, PACKAGING = :packaging, " +
            "PROCESSING = :processing, DISTANCE = :distance, CATEGORY_ID = :categoryId " +
            "WHERE ID = :id AND COMPANY_ID = :companyId")
    boolean update(@BindBean TenderRecord lot);

    @SqlUpdate("UPDATE TENDER " +
            "SET STATUS = :status " +
            "WHERE ID = :id AND COMPANY_ID = :companyId")
    boolean setStatus(@Bind("id") UUID id, @Bind("companyId") UUID companyId, @Bind("status") BidStatus status);

    @SqlUpdate("DELETE FROM TENDER WHERE PRODUCT_ID = :productId")
    void deleteByProductId(@Bind("productId") UUID productId);

    @SqlUpdate("DELETE FROM TENDER WHERE PRODUCT_ID = :categoryId")
    void deleteByCategoryId(@Bind("categoryId") UUID categoryId);
}
