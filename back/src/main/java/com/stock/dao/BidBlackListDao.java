package com.stock.dao;

import com.stock.entity.business.BidBlackListRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterBeanMapper(BidBlackListRecord.class)
public interface BidBlackListDao {

    @SqlUpdate("INSERT INTO BLACK_LIST (LOT_ID, TENDER_ID) " +
            " SELECT LOT_ID, TENDER_ID " +
            " FROM DEAL " +
            " WHERE ID = :dealId")
    void createForDeal(@Bind("dealId") UUID dealId);

    @SqlQuery("SELECT * FROM BLACK_LIST")
    List<BidBlackListRecord> getAll();

    @SqlUpdate("DELETE FROM BLACK_LIST " +
            "WHERE LOT_ID NOT IN (SELECT ID FROM LOT) " +
            "OR TENDER_ID NOT IN (SELECT ID FROM TENDER)")
    boolean clean();

}
