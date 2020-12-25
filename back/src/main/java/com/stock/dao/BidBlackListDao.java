package com.stock.dao;

import com.stock.entity.business.BidBlackListRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(BidBlackListRecord.class)
public interface BidBlackListDao {

    @SqlUpdate("INSERT INTO BLACK_LIST (LOT_ID, TENDER_ID) " +
            " VALUES (:lotId, :tenderId)")
    void create(@BindBean BidBlackListRecord address);

    @SqlQuery("SELECT * FROM BLACK_LIST")
    List<BidBlackListRecord> getAll();

    @SqlUpdate("DELETE FROM BLACK_LIST " +
            "WHERE LOT_ID NOT IN (SELECT ID FROM LOT) " +
            "OR TENDER_ID NOT IN (SELECT ID FROM TENDER)")
    boolean clean();

}
