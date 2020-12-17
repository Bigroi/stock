package com.stock.dao;

import com.stock.entity.Language;
import com.stock.entity.business.UserRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(UserRecord.class)
public interface UserDao {

    @SqlUpdate("INSERT INTO SYSTEM_USER (USER_NAME, PASSWORD, COMPANY_ID, ROLES, LANGUAGE) "
            + " VALUES (:userName, :password, :companyId, CAST(:roles as json), :language)")
    void create(@BindBean UserRecord user);

    @SqlQuery("SELECT * FROM SYSTEM_USER WHERE USER_NAME = :userName")
    Optional<UserRecord> getByUsername(@Bind("userName") String username);

    @SqlUpdate("UPDATE SYSTEM_USER " +
            " SET REFRESH_TOKEN_ISSUED = :refreshTokenIssued " +
            " WHERE USER_NAME = :userName")
    void updateRefreshSecret(
            @Bind("userName") String userName,
            @Bind("refreshTokenIssued") Date refreshTokenIssued
    );

    @SqlUpdate("UPDATE SYSTEM_USER " +
            " SET USER_NAME = :newUserName, PASSWORD = :password, LANGUAGE = :language" +
            " WHERE USER_NAME = :userName")
    void updateAccountData(
            @Bind("newUserName") String username,
            @Bind("password") String password,
            @Bind("language") Language language,
            @Bind("userName") String userName);
}
