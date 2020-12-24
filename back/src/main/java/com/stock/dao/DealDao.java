package com.stock.dao;

import com.stock.entity.business.DealRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

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
}
