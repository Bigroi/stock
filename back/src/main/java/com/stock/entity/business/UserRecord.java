package com.stock.entity.business;

import com.stock.entity.Language;
import com.stock.security.Role;
import org.jdbi.v3.json.Json;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserRecord {

    private String userName;
    private String password;
    private UUID companyId;
    private List<Role> roles;
    private Date refreshTokenIssued;
    private Language language;

    public UserRecord() {

    }

    public UserRecord(
            String userName,
            String password,
            UUID companyId,
            List<Role> roles,
            Date refreshTokenIssued,
            Language language
    ) {
        this.userName = userName;
        this.password = password;
        this.companyId = companyId;
        this.roles = roles;
        this.refreshTokenIssued = refreshTokenIssued;
        this.language = language;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    @Json
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Date getRefreshTokenIssued() {
        return refreshTokenIssued;
    }

    public void setRefreshTokenIssued(Date refreshTokenIssued) {
        this.refreshTokenIssued = refreshTokenIssued;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
