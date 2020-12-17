package com.stock.dao;

import com.stock.entity.business.AddressRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.UUID;

@RegisterBeanMapper(AddressRecord.class)
public interface AddressDao {

    @SqlUpdate("INSERT INTO ADDRESS (ID, CITY, COUNTRY, ADDRESS, LATITUDE, LONGITUDE, COMPANY_ID, PRIMARY_ADDRESS) "
            + " VALUES (:id, :city, :country, :address, :latitude, :longitude, :companyId, :primaryAddress)")
    void create(@BindBean AddressRecord user);

    @SqlQuery("SELECT * FROM ADDRESS " +
            "WHERE COMPANY_ID = :companyId AND PRIMARY_ADDRESS = TRUE")
    AddressRecord getPrimaryByCompanyId(@Bind("companyId") UUID companyId);
}
