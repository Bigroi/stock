package com.stock.dao;

import com.stock.entity.BidStatus;
import com.stock.entity.business.LotRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(LotRecord.class)
public interface LotDao {

    @SqlQuery("SELECT * FROM LOT WHERE COMPANY_ID = :companyId")
    List<LotRecord> getByCompanyId(@Bind("companyId") UUID companyId);

    @SqlQuery("SELECT * FROM LOT WHERE COMPANY_ID = :companyId AND ID = :id")
    Optional<LotRecord> getByIdAndCompanyId(@Bind("id") UUID id, @Bind("companyId") UUID companyId);

    @SqlUpdate("DELETE FROM LOT WHERE COMPANY_ID = :companyId AND ID = :id")
    boolean deleteByIdAndCompanyId(@Bind("id") UUID id, @Bind("companyId") UUID companyId);

    @SqlUpdate("INSERT INTO LOT (ID, DESCRIPTION, PRICE, MIN_VOLUME, MAX_VOLUME, COMPANY_ID, STATUS, CREATION_DATE, " +
            "EXPIRATION_DATE, ADDRESS_ID, DISTANCE, CATEGORY_ID, ALERT, PHOTO) " +
            "VALUES (:id, :description, :price, :minVolume, :maxVolume, :companyId, :status, :creationDate, " +
            ":expirationDate, :addressId, :distance, :categoryId, :alert, :photo)")
    void create(@BindBean LotRecord lot);

    @SqlUpdate("UPDATE LOT " +
            "SET DESCRIPTION = :description, PRICE = :price, MIN_VOLUME = :minVolume, MAX_VOLUME = :maxVolume, " +
            "STATUS = :status, EXPIRATION_DATE = :expirationDate, ADDRESS_ID = :addressId, DISTANCE = :distance, " +
            "CATEGORY_ID = :categoryId, PHOTO = :photo " +
            "WHERE ID = :id AND COMPANY_ID = :companyId")
    boolean update(@BindBean LotRecord lot);

    @SqlUpdate("UPDATE LOT " +
            "SET STATUS = :status " +
            "WHERE ID = :id AND COMPANY_ID = :companyId")
    boolean setStatus(@Bind("id") UUID id, @Bind("companyId") UUID companyId, @Bind("status") BidStatus status);

    @SqlUpdate("DELETE FROM LOT " +
            "WHERE CATEGORY_ID IN (" +
            "       SELECT ID FROM PRODUCT_CATEGORY " +
            "       WHERE PRODUCT_ID = :productId" +
            ")")
    void deleteByProductId(@Bind("productId") UUID productId);

    @SqlUpdate("DELETE FROM LOT WHERE CATEGORY_ID = :categoryId")
    void deleteByCategoryId(@Bind("categoryId") UUID categoryId);

    @SqlUpdate("UPDATE LOT " +
            "SET STATUS = :status " +
            "WHERE COMPANY_ID = :companyId")
    void setStatusByCompanyId(@Bind("companyId") UUID companyId, @Bind("status") BidStatus inactive);
}
