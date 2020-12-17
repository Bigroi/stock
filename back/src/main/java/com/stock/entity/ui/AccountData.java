package com.stock.entity.ui;

import com.stock.entity.Language;

public class AccountData {

    private String username;
    private String phone;
    private Language language;
    private String companyName;
    private String regNumber;
    private String password;
    private double latitude;
    private double longitude;

    public AccountData(){

    }

    public AccountData(
            String username,
            String phone,
            Language language,
            String companyName,
            String regNumber,
            String password,
            double latitude,
            double longitude
    ) {
        this.username = username;
        this.phone = phone;
        this.language = language;
        this.companyName = companyName;
        this.regNumber = regNumber;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
