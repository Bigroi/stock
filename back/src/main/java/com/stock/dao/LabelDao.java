package com.stock.dao;

import com.stock.entity.business.LabelRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(LabelRecord.class)
public interface LabelDao {

    @SqlQuery("SELECT * FROM LABEL")
    List<LabelRecord> getAll();

    @SqlQuery("SELECT * FROM LABEL WHERE NAME = :name")
    Optional<LabelRecord> getByName(@Bind("name") String name);

    @SqlBatch("INSERT INTO LABEL (NAME, LANGUAGE, VALUE)" +
            " VALUES (:name, :language, :value)")
    void create(@BindBean List<LabelRecord> records);

    @SqlBatch("UPDATE LABEL " +
            " SET VALUE = :value " +
            " WHERE NAME = :name AND LANGUAGE = :language")
    boolean[] update(@BindBean List<LabelRecord> records);

    @SqlUpdate("DELETE FROM LABEL WHERE NAME = :name")
    boolean deleteByName(@Bind("name") String name);
}
