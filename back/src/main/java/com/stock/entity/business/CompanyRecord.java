package com.stock.entity.business;

import com.stock.entity.CompanyStatus;

import java.io.Serializable;
import java.util.UUID;

public class CompanyRecord implements Serializable {

    private UUID id;
    private String name;
    private String phone;
    private String regNumber;
    private CompanyStatus status;

    public CompanyRecord() {

    }

    public CompanyRecord(UUID id, String name, String phone, String regNumber, CompanyStatus status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.regNumber = regNumber;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public void setStatus(CompanyStatus status) {
        this.status = status;
    }

}
