package com.stock.dao;

import com.stock.entity.business.ProductCategoryRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterBeanMapper(ProductCategoryRecord.class)
public interface ProductCategoryDao {

    @SqlQuery("SELECT * FROM PRODUCT_CATEGORY WHERE PRODUCT_ID = :productId")
    List<ProductCategoryRecord> getByproductId(@Bind("productId") UUID productId);

    @SqlUpdate("INSERT INTO PRODUCT_CATEGORY (ID, CATEGORY_NAME, PRODUCT_ID, REMOVED) " +
            "VALUES (:id, :categoryName, :productId, :removed)")
    void create(@BindBean ProductCategoryRecord record);

    @SqlUpdate("UPDATE PRODUCT_CATEGORY " +
            "SET REMOVED = :removed " +
            "WHERE ID = :id AND PRODUCT_ID = :productId")
    boolean setRemovedByIdAndProductId(
            @Bind("id") UUID id,
            @Bind("productId") UUID productId,
            @Bind("removed") boolean removed
    );

    @SqlUpdate("UPDATE PRODUCT_CATEGORY " +
            "SET REMOVED = :removed " +
            "WHERE PRODUCT_ID = :productId")
    void setRemovedByProductId(
            @Bind("productId") UUID productId,
            @Bind("removed") boolean removed
    );
}
