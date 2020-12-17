package com.stock.entity.ui;

import com.stock.security.Role;

import java.util.List;

public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;
    private final String email;
    private final List<Role> roles;
    private final String language;
    private final String companyName;

    public LoginResponse(
            String accessToken,
            String refreshToken,
            String email,
            List<Role> roles,
            String language,
            String companyName
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.roles = roles;
        this.language = language;
        this.companyName = companyName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getLanguage() {
        return language;
    }

    public String getCompanyName() {
        return companyName;
    }
}
