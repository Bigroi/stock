package com.stock.dao;

import com.stock.entity.business.AddressRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterBeanMapper(AddressRecord.class)
public interface AddressDao {

    @SqlUpdate("INSERT INTO ADDRESS (ID, CITY, COUNTRY, ADDRESS, LATITUDE, LONGITUDE, COMPANY_ID, PRIMARY_ADDRESS) " +
            " VALUES (:id, :city, :country, :address, :latitude, :longitude, :companyId, :primaryAddress)")
    void create(@BindBean AddressRecord address);

    @SqlQuery("SELECT * FROM ADDRESS " +
            "WHERE COMPANY_ID = :companyId AND PRIMARY_ADDRESS = TRUE")
    AddressRecord getPrimaryByCompanyId(@Bind("companyId") UUID companyId);

    @SqlQuery("SELECT * FROM ADDRESS WHERE COMPANY_ID = :companyId")
    List<AddressRecord> getByCompanyId(@Bind("companyId") UUID companyId);

    @SqlUpdate("UPDATE ADDRESS " +
            "SET CITY = :city, COUNTRY = :country, ADDRESS = :address, LATITUDE = :latitude, LONGITUDE = :longitude " +
            " WHERE ID = :id ADN COMPANY_ID = :companyId")
    boolean update(@BindBean AddressRecord address);

    @SqlUpdate("UPDATE ADDRESS " +
            "SET PRIMARY_ADDRESS = (:id = ID) " +
            " WHERE COMPANY_ID = :companyId")
    boolean setPrimary(@Bind("id") UUID id, @Bind("companyId") UUID companyId);

    @SqlUpdate("DELETE FROM ADDRESS " +
            " WHERE ID = :id AND COMPANY_ID = :companyId AND PRIMARY_ADDRESS = FALSE")
    boolean removeByIdAndCompanyId(@Bind("id") UUID id, @Bind("companyId") UUID companyId);
}
