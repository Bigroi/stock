package com.stock.dao;

import com.stock.entity.Language;
import com.stock.entity.business.LabelRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RegisterBeanMapper(LabelRecord.class)
public interface LabelDao {

    @SqlQuery("SELECT * FROM LABEL")
    List<LabelRecord> getAll();

    @SqlQuery("SELECT * FROM LABEL WHERE NAME = :name")
    List<LabelRecord> getByNameInternal(@Bind("name") String name);

    default Map<Language, String> getByName(String name) {
        return getByNameInternal(name).stream()
                .collect(Collectors.toMap(LabelRecord::getLanguage, LabelRecord::getValue));
    }

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
