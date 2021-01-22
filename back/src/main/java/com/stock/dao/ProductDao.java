package com.stock.dao;

import com.stock.entity.business.ProductCategoryRecord;
import com.stock.entity.business.ProductRecord;
import org.jdbi.v3.core.mapper.JoinRow;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterJoinRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RegisterBeanMapper(ProductRecord.class)
public interface ProductDao {

    @RegisterBeanMapper(value = ProductRecord.class, prefix = "P")
    @RegisterBeanMapper(value = ProductCategoryRecord.class, prefix = "C")
    @RegisterJoinRowMapper({ProductRecord.class, ProductCategoryRecord.class})
    @SqlQuery("SELECT P.ID P_ID, P.NAME P_NAME, C.ID C_ID, P.PICTURE P_PICTURE, C.CATEGORY_NAME C_CATEGORY_NAME " +
            "FROM PRODUCT P " +
            "JOIN PRODUCT_CATEGORY C " +
            "ON P.ID = C.PRODUCT_ID " +
            "WHERE P.REMOVED = FALSE AND C.REMOVED = FALSE")
    Stream<JoinRow> getActiveProductWithCategoriesInternal();

    default Map<ProductRecord, List<ProductCategoryRecord>> getActiveProductWithCategories() {
        return getActiveProductWithCategoriesInternal()
                .collect(Collectors.groupingBy(
                        jr -> jr.get(ProductRecord.class),
                        Collectors.mapping(jr -> jr.get(ProductCategoryRecord.class), Collectors.toList())
                        )
                );
    }

    default Map<UUID, ProductRecord> getActiveProductNameByCategory() {
        return getActiveProductWithCategoriesInternal()
                .collect(Collectors.toMap(
                        jr -> jr.get(ProductCategoryRecord.class).getId(),
                        jr -> jr.get(ProductRecord.class)
                ));
    }

    @SqlQuery("SELECT * FROM PRODUCT WHERE REMOVED = FALSE")
    Stream<ProductRecord> getActiveProducts();

    @SqlQuery("SELECT * FROM PRODUCT P ")
    List<ProductRecord> getAll();

    @SqlUpdate("INSERT INTO PRODUCT (ID, NAME, REMOVED, PICTURE) " +
            "VALUES (:id, :name, :removed, :picture)")
    void create(@BindBean ProductRecord record);

    @SqlUpdate("UPDATE PRODUCT " +
            "SET REMOVED = :removed " +
            "WHERE ID = :id")
    boolean setRemovedById(@Bind("id") UUID id, @Bind("removed") boolean removed);

    @SqlQuery("SELECT * FROM PRODUCT P WHERE ID = :id AND REMOVED = FALSE")
    Optional<ProductRecord> getById(@Bind("id") UUID id);
}
