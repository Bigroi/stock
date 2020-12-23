package com.stock.dao;

import com.stock.entity.business.ProductCategoryRecord;
import com.stock.entity.business.ProductRecord;
import org.jdbi.v3.core.mapper.JoinRow;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterJoinRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RegisterBeanMapper(ProductRecord.class)
public interface ProductDao {

    //    @UseRowReducer(ProductWithCategoriesReducer.class)
    @RegisterBeanMapper(value = ProductRecord.class, prefix = "P")
    @RegisterBeanMapper(value = ProductCategoryRecord.class, prefix = "C")
    @RegisterJoinRowMapper({ProductRecord.class, ProductCategoryRecord.class})
    @SqlQuery("SELECT P.ID P_ID, P.NAME P_NAME, C.ID C_ID, C.CATEGORY_NAME C_CATEGORY_NAME " +
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
}
