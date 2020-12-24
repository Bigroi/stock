package com.stock.dao;

import com.stock.entity.CompanyStatus;
import com.stock.entity.business.CompanyRecord;
import com.stock.entity.business.UserRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(CompanyRecord.class)
public interface CompanyDao {

    @SqlUpdate("INSERT INTO COMPANY (ID, NAME, PHONE, REG_NUMBER, STATUS) "
            + " VALUES (:id, :name, :phone, :regNumber, :status)")
    void create(@BindBean CompanyRecord user);

    @SqlQuery("SELECT * FROM COMPANY WHERE NAME = :name")
    Optional<CompanyRecord> getByName(@Bind("name") String name);

    @SqlQuery("SELECT * FROM COMPANY WHERE REG_NUMBER = :regNumber")
    Optional<CompanyRecord> getByRegNumber(@Bind("regNumber") String regNumber);

    @SqlQuery("SELECT * FROM COMPANY WHERE ID = :id")
    CompanyRecord getByID(@Bind("id") UUID id);

    @SqlUpdate("UPDATE COMPANY " +
            " SET PHONE = :phone " +
            " WHERE ID = :id")
    void updatePhone(@Bind("phone") String phone, @Bind("id") UUID id);

    @SqlQuery("SELECT * FROM COMPANY")
    List<CompanyRecord> getAllCompanies();

    @SqlUpdate("UPDATE COMPANY SET STATUS = :status WHERE ID = :id")
    boolean changeCompanyStatus(@Bind("id") UUID id, @Bind("status") CompanyStatus status);
}
